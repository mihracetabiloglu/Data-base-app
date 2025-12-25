package kutuphane.kutuphane_otomasyonu.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Penalties;
public interface PenaltiesRepostory extends JpaRepository<Penalties, Long> {

}
