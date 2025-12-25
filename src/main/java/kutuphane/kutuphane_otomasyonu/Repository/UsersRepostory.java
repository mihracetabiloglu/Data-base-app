package kutuphane.kutuphane_otomasyonu.Repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import kutuphane.kutuphane_otomasyonu.model.Users;
@Repository
public interface UsersRepostory extends JpaRepository<Users, Long>{
Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

}
