package kutuphane.kutuphane_otomasyonu.Repostory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Author;
public interface AuthorRepostory extends JpaRepository<Author, Long> {

}
