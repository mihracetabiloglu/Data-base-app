package kutuphane.kutuphane_otomasyonu.service;
import kutuphane.kutuphane_otomasyonu.model.Author;
import kutuphane.kutuphane_otomasyonu.Repository.AuthorRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service // Spring'e bunun bir İş Mantığı sınıfı olduğunu söyler
public class AuthorService {
@Autowired // Repository'yi buraya otomatik bağlar (Inject eder)
    private AuthorRepostory authorRepository;

    // Tüm yazarları getiren işlem
    public List<Author> tumYazarlariGetir() {
        return authorRepository.findAll();
    }

    // Yeni yazar ekleyen işlem
    public Author yazarEkle(Author author) {
        return authorRepository.save(author); // Veritabanına kaydeder
    }
   // isim ile yazar arama işlemi
    public Object searchByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }
   // Tüm yazarları getirme işlemi
    public Object getAllAuthors() {
        return authorRepository.findAll();
    }
    // Yeni yazar ekleme işlemi
    public Object addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);       
    }
    }
    



