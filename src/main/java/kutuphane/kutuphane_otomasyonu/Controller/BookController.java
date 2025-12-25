package kutuphane.kutuphane_otomasyonu.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.model.Book;
import kutuphane.kutuphane_otomasyonu.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    //  TÜM KİTAPLARI GETİR
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    //  ID'YE GÖRE KİTAP GETİR
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }
   // İSME GÖRE KİTAP ARA
    @GetMapping("/search")
    public ResponseEntity<Object> searchBooksByName(@RequestParam String name) {
        return ResponseEntity.ok(bookService.searchBooksByName(name));
    }
    @PreAuthorize("hasRole('ADMIN')")
    //  YENİ KİTAP EKLE (ADMIN)
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.addBook(book));
    }
    //  YAZARA GÖRE KİTAP ARA
    @GetMapping("/searchByAuthor")
    public ResponseEntity<Object> searchBooksByAuthor(@RequestParam String author) {
    return ResponseEntity.ok(bookService.searchBooksByAuthor(author));
     }

    
@PreAuthorize("hasRole('ADMIN')")
    //  KİTAP SİL (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

