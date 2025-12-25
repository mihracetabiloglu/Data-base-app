package kutuphane.kutuphane_otomasyonu.service;
import kutuphane.kutuphane_otomasyonu.model.Book;
import kutuphane.kutuphane_otomasyonu.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service // Spring'e bunun bir İş Mantığı sınıfı olduğunu söyler
public class BookService {
@Autowired // Repository'yi buraya otomatik bağlar (Inject eder)
    private BookRepository bookRepository;
    // Tüm kitapları getiren işlem
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }   
    // Yeni kitap ekleyen işlem
    public Book addBook(Book book) {
        return bookRepository.save(book); // Veritabanına kaydeder
    }
    // kitap silme işlemi
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);       
    }
    // id'ye göre kitap getirme işlemi
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    public Object searchBooksByName(String name) {
        return bookRepository.findByTitleContainingIgnoreCase(name);
    }
    public Object searchBooksByAuthor(String author) {
        return bookRepository.findByTitleContainingIgnoreCase(author);
    }
        
}
