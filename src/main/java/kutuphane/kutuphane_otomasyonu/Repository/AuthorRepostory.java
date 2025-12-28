package kutuphane.kutuphane_otomasyonu.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Author;
public interface AuthorRepostory extends JpaRepository<Author, Long> {

    Object findByNameContainingIgnoreCase(String name);
    Optional<Author> findByName(String name);
}
