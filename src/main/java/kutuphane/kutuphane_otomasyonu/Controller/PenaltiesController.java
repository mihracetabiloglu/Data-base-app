package kutuphane.kutuphane_otomasyonu.Controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.model.Penalties;
import kutuphane.kutuphane_otomasyonu.service.PenaltiesService;

@RestController
@RequestMapping("/api/penalties")
public class PenaltiesController {

    private final PenaltiesService penaltiesService;

    
    public PenaltiesController(PenaltiesService penaltiesService) {
        this.penaltiesService = penaltiesService;
    }
@PreAuthorize("hasRole('ADMIN')")
    // Tüm cezaları listele 
    @GetMapping
    public List<Penalties> getAllPenalties() {
        return penaltiesService.getAllPenalties();
    }
@PreAuthorize("hasRole('ADMIN')")
    // ID'ye göre ceza bul 
    @GetMapping("/{id}")
    public ResponseEntity<Penalties> getPenaltiesById(@PathVariable Long id) {
        Penalties penalty = penaltiesService.getPenaltiesById(id);
        return penalty != null ? ResponseEntity.ok(penalty) : ResponseEntity.notFound().build();
    }
@PreAuthorize("hasRole('ADMIN')")
    // Manuel ceza ekle (Örn: Kitaba zarar verme) 
    @PostMapping
    public Penalties savePenalty(@RequestBody Penalties penalties) {
        return penaltiesService.savePenalty(penalties);
    }
@PreAuthorize("hasRole('ADMIN')")
    // Ceza kaydını sil (Örn: Ceza ödendiğinde) 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePenalties(@PathVariable Long id) {
        penaltiesService.deletePenalties(id);
        return ResponseEntity.ok().build();
    }
}
