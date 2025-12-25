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
   mailService.sendMail(
    "Yeni Ödünç Alma",
    user.getUsers_name() + " adlı kullanıcı " + book.getTitle() + " kitabını ödünç aldı."
);
    bookRepository.save(book);
    return loanRepository.save(loan);
 
}
    @Transactional
  public void kitapIadeEt(Long loanId) {

    Loan loan = loanRepository.findById(loanId)
            .orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı"));

    // Zaten iade edilmiş mi?
    if (loan.getReturnDate() != null) {
        throw new RuntimeException("Bu kitap zaten iade edilmiş");
    }

    loan.setReturnDate(LocalDateTime.now());

    // Kitap stoğunu artır
    Book book = loan.getBook();
    book.setAvailable_copies(book.getAvailable_copies() + 1);

    // Süre aşımı kontrolü
    if (loan.getReturnDate().isAfter(loan.getDueDate())) {

        long lateDays = ChronoUnit.DAYS.between(
                loan.getDueDate(),
                loan.getReturnDate()
        );

        double penaltyAmount = lateDays * 10; // günlük 10 TL ceza

       Penalties penalty = new Penalties();
    penalty.setLoan(loan);
    penalty.setPenalty_amount(penaltyAmount);
    penalty.setPenalty_reason("Kitap " + lateDays + " dakika geç teslim edildi");
         penaltiesRepository.save(penalty);
    }

    bookRepository.save(book);
    loanRepository.save(loan);
    
}


}
