package kutuphane.kutuphane_otomasyonu.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.Repository.UsersRepostory;
import kutuphane.kutuphane_otomasyonu.model.Loan;
import kutuphane.kutuphane_otomasyonu.model.Users;
import kutuphane.kutuphane_otomasyonu.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    
    @Autowired
    private LoanService loanService;
    @Autowired
    private UsersRepostory usersRepository;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tumoduncler")
    public ResponseEntity<?> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Hem User hem Admin ödünç alabilsin
   // Kitap Ödünç Al -> POST http://localhost:8080/api/loans/borrow?bookId=1&userId=1
    @PostMapping("/borrow")
    public ResponseEntity<?> borrowBook(@RequestParam Long bookId, @RequestParam Long userId) {
        try {
            Loan loan = loanService.oduncAl(bookId, userId);
            return ResponseEntity.ok(loan);
        } catch (RuntimeException e) {
            // Hata mesajını (Stok yok, kullanıcı yok vb.) kullanıcıya dönüyoruz
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // Hem User hem Admin ödünç alabilsin
   // Kitap İade Et -> POST http://localhost:8080/api/loans/return/5 (5 buradaki loanId)
    @PostMapping("/return/{loanId}")
    public ResponseEntity<?> returnBook(@PathVariable Long loanId) {
        try {
            
            loanService.kitapIadeEt(loanId);
            return ResponseEntity.ok("Kitap başarıyla iade edildi. Ceza durumu kontrol edildi.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@GetMapping("/my-loans")
public ResponseEntity<?> getMyLoans() {
    // 1. Token'dan gelen kimliği (genelde email olur) al
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String userEmail = auth.getName(); 

    // 2. Bu email ile kullanıcıyı veritabanından çek
    // (usersRepository veya userService kullanarak)
    Users user = usersRepository.findByEmail(userEmail)
                  .orElse(null);

    if (user != null) {
        // 3. Kullanıcının gerçek ID'si ile ödünçlerini getir
        List<Loan> myLoans = loanService.getLoansByUserId(user.getUsers_ID());
        return ResponseEntity.ok(myLoans);
    } else {
        return ResponseEntity.status(401).body("Kullanıcı oturum bilgisi eksik.");
    }
}
}

