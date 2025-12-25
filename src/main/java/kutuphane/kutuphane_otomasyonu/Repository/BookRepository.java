package kutuphane.kutuphane_otomasyonu.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kutuphane.kutuphane_otomasyonu.model.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthors_NameContainingIgnoreCase(String name);
}
