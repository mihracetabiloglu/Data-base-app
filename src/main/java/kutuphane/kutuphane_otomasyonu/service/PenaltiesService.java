package kutuphane.kutuphane_otomasyonu.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import kutuphane.kutuphane_otomasyonu.Repository.PenaltiesRepostory;
import kutuphane.kutuphane_otomasyonu.model.Penalties;

@Service
public class PenaltiesService {

    private final PenaltiesRepostory penaltiesRepository;

    public PenaltiesService(PenaltiesRepostory penaltiesRepository) {
        this.penaltiesRepository = penaltiesRepository;
    }

    public List<Penalties> getAllPenalties() {
        return penaltiesRepository.findAll();
    }

    public Penalties getPenaltiesById(Long id) {
        return penaltiesRepository.findById(id).orElse(null);
    }

    public Penalties savePenalty(Penalties penalties) {
        return penaltiesRepository.save(penalties);
    }

    public void deletePenalties(Long id) {
        penaltiesRepository.deleteById(id);
    }
}

