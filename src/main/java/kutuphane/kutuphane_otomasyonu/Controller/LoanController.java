package kutuphane.kutuphane_otomasyonu.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.model.Loan;
import kutuphane.kutuphane_otomasyonu.service.LoanService;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

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
}
