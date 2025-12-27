package kutuphane.kutuphane_otomasyonu.Repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kutuphane.kutuphane_otomasyonu.model.Loan;
public interface LoanRepostory extends JpaRepository<Loan, Long> {

    @Query("SELECT l FROM Loan l WHERE l.user.users_ID = :userId")
List<Loan> findByUserId(@Param("userId") Long userId);

}
