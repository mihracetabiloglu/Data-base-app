package kutuphane.kutuphane_otomasyonu.service;
import kutuphane.kutuphane_otomasyonu.Repository.BookRepository;
import kutuphane.kutuphane_otomasyonu.Repository.LoanRepostory;
import kutuphane.kutuphane_otomasyonu.Repository.PenaltiesRepostory;
import kutuphane.kutuphane_otomasyonu.Repository.UsersRepostory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import kutuphane.kutuphane_otomasyonu.model.Book;
import kutuphane.kutuphane_otomasyonu.model.Loan;
import kutuphane.kutuphane_otomasyonu.model.Penalties;
import kutuphane.kutuphane_otomasyonu.model.Users;

@Service
public class LoanService {
    @Autowired
 private final LoanRepostory loanRepository;
    private final BookRepository bookRepository;
    private final UsersRepostory userRepository;
    private final PenaltiesRepostory penaltiesRepository;
    private final MailService mailService;
    public LoanService(
            LoanRepostory loanRepository,
            BookRepository bookRepository,
            UsersRepostory userRepository,
            PenaltiesRepostory penaltiesRepository,
            MailService mailService) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.penaltiesRepository = penaltiesRepository;
        this.mailService = mailService;
    }

   @Transactional
public Loan oduncAl(Long bookId, Long userId) {

    Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));

    Users user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

    if (book.getAvailable_copies() <= 0) {
        throw new RuntimeException("Kitap stokta yok");
    }

    // Stok düş
    book.setAvailable_copies(book.getAvailable_copies() - 1);

    Loan loan = new Loan();
    loan.setBook(book);
    loan.setUser(user);
    loan.setLoanDate(LocalDate.now());
    loan.setDueDate(LocalDateTime.now().plusMinutes(1)); // 1 dakika ödünç süresi
    loan.setReturnDate(null);
    mailService.sendMail(loan.getUser().getEmail(), "Kitap Ödünç Alma Onayı", 
            "Sayın " + loan.getUser().getUsers_name() + ",\n\n" +
            "Ödünç aldığınız '" + loan.getBook().getTitle() + "' iade süresi 1 dakikadır gecikme halinde her dakika " +
            "için 10 tl ücret tahsil edilecektir." +
            "\n\nKutuphane Otomasyonu");
    bookRepository.save(book);
    return loanRepository.save(loan);
 
}

public double calculateFine(Long loanId) {
    Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı"));
    
    LocalDateTime now = LocalDateTime.now();
    if (now.isAfter(loan.getDueDate())) {
        long lateMinutes = ChronoUnit.MINUTES.between(loan.getDueDate(), now);
        return lateMinutes * 10.0; // Dakika başı 10 TL
    }
    return 0.0;
}

@Transactional
public void kitapIadeEt(Long loanId, boolean isFinePaid) {
    Loan loan = loanRepository.findById(loanId).orElseThrow();
    if (loan.getReturnDate() != null) throw new RuntimeException("Zaten iade edilmiş");

    loan.setReturnDate(LocalDateTime.now());
    
    // Ceza kaydı oluştur
    double fineAmount = calculateFine(loanId);
    if (fineAmount > 0) {
        Penalties penalty = new Penalties();
        penalty.setLoan(loan);
        penalty.setPenalty_amount(fineAmount);
        penalty.setPenalty_reason("Gecikme cezası");
        penalty.setIs_paid(isFinePaid); // Web'den gelen onay bilgisi
        penaltiesRepository.save(penalty);
        mailService.sendMail(loan.getUser().getEmail(), "Kitap İade Onayı", 
            "Sayın " + loan.getUser().getUsers_name() + ",\n\n" +
            "Ödünç aldığınız '" + loan.getBook().getTitle() + "' kitabını iade ettiniz.\n" +
            "Gecikme cezası: " + fineAmount + " TL.\n" +
            (isFinePaid ? "Ceza ödemeniz alınmıştır. Teşekkür ederiz." : "Lütfen cezanızı en kısa sürede ödeyiniz.") +
            "\n\nKutuphane Otomasyonu");
    }

    Book book = loan.getBook();
    book.setAvailable_copies(book.getAvailable_copies() + 1);
    
    bookRepository.save(book);
    loanRepository.save(loan);
}

    public Object getAllLoans() {
        return loanRepository.findAll();
    }

   public List<Loan> getLoansByUserId(Long userId) {
    return loanRepository.findByUserId(userId);
}


}
