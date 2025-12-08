package kutuphane.kutuphane_otomasyonu.Repostory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Category;
@Repository
public interface CategoryRepostory extends JpaRepository<Category, Long> {

}
