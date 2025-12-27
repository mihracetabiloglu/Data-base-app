package kutuphane.kutuphane_otomasyonu.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import kutuphane.kutuphane_otomasyonu.Security.JwtUtil;
import kutuphane.kutuphane_otomasyonu.model.Users;
import kutuphane.kutuphane_otomasyonu.service.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;
    @Autowired
    private JwtUtil jwtUtil;
    //  KULLANICI KAYIT
    @PostMapping("/register")
    public ResponseEntity<Users> register(@RequestBody Users user) {
        Users savedUser = usersService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    //  GİRİŞ (Admin + User)
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Users loginData) {

        Users user = usersService.login(loginData.getEmail(), loginData.getPassword());
        String token = jwtUtil.generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole());
        response.put("userId", user.getUsers_ID());
        response.put("userName", user.getUsers_name());
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('ADMIN')")
    // SADECE ADMİN – TÜM KULLANICILARI LİSTELE
    @GetMapping("/admin/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        return ResponseEntity.ok(usersService.getAllUsers());
    }
}

