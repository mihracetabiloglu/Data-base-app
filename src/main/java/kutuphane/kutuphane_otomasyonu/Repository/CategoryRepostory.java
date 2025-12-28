package kutuphane.kutuphane_otomasyonu.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Book;
import kutuphane.kutuphane_otomasyonu.model.Category;
@Repository
public interface CategoryRepostory extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
