package kutuphane.kutuphane_otomasyonu.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.model.Users;
import kutuphane.kutuphane_otomasyonu.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    //  KULLANICI KAYIT
    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users savedUser = usersService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    //  GİRİŞ (Admin + User)
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password) {

        Users user = usersService.login(email, password);

        return ResponseEntity.ok(user);
    }

    // SADECE ADMİN – TÜM KULLANICILARI LİSTELE
    @GetMapping("/admin/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}

