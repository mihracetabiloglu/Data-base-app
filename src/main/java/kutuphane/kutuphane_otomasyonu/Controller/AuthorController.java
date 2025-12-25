package kutuphane.kutuphane_otomasyonu.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import kutuphane.kutuphane_otomasyonu.model.Author;
import kutuphane.kutuphane_otomasyonu.service.AuthorService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    //  Yazar adına göre kitaplarıyla birlikte getir
    // GET /api/authors/search?name=Sabahattin
    @GetMapping("/search")
    public ResponseEntity<Object> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(authorService.searchByName(name));
    }
    @PreAuthorize("hasRole('ADMIN')")
    //  Yazar ekle (ADMIN)
    @PostMapping
    public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.addAuthor(author));
    }
    @PreAuthorize("hasRole('ADMIN')")
    //  Yazar sil (ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
    //  Tüm yazarları getir
    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }
}

