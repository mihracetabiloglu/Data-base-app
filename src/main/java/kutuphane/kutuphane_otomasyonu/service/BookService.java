package kutuphane.kutuphane_otomasyonu.service;
import kutuphane.kutuphane_otomasyonu.model.Author;
import kutuphane.kutuphane_otomasyonu.model.Book;
import kutuphane.kutuphane_otomasyonu.model.Category;
import kutuphane.kutuphane_otomasyonu.Repository.AuthorRepostory;
import kutuphane.kutuphane_otomasyonu.Repository.BookRepository;
import kutuphane.kutuphane_otomasyonu.Repository.CategoryRepostory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service // Spring'e bunun bir İş Mantığı sınıfı olduğunu söyler
public class BookService {
@Autowired // Repository'yi buraya otomatik bağlar (Inject eder)
    private BookRepository bookRepository;
@Autowired
    private AuthorRepostory authorRepository;
@Autowired
    private CategoryRepostory categoryRepository;
    // Tüm kitapları getiren işlem
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    } 
    @Transactional
    // Yeni kitap ekleyen işlem
    public Book addBook(Book book) {

        // 1. Yazarları Yönet (ManyToMany)
    if (book.getAuthors() != null && !book.getAuthors().isEmpty()) {
        Set<Author> kaliciYazarlar = new HashSet<>();
        for (Author gelenYazar : book.getAuthors()) {
            Author author = authorRepository.findByName(gelenYazar.getName())
                .orElseGet(() -> {
                    Author yeni = new Author();
                    yeni.setName(gelenYazar.getName());
                    return authorRepository.save(yeni);
                });
            kaliciYazarlar.add(author);
            System.out.println("Veritabanına kaydedilen yazar: " + author.getName());
        }
        
        book.setAuthors(kaliciYazarlar);
    }
  
    // 2. Kategorileri Yönet
    if (book.getCategories() != null && !book.getCategories().isEmpty()) {
        Set<Category> kaliciKategoriler = new HashSet<>();
        for (Category gelenKat : book.getCategories()) {
            Category category = categoryRepository.findByName(gelenKat.getName())
                .orElseGet(() -> {
                    Category yeni = new Category();
                    yeni.setName(gelenKat.getName());
                    return categoryRepository.save(yeni);
                });
            kaliciKategoriler.add(category);
        }
        book.setCategories(kaliciKategoriler);
    }
    
        return bookRepository.save(book); // Veritabanına kaydeder
    }
    
    @Transactional
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
