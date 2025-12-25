package kutuphane.kutuphane_otomasyonu.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kutuphane.kutuphane_otomasyonu.Repository.UsersRepostory;
import kutuphane.kutuphane_otomasyonu.Security.JwtUtil;
import kutuphane.kutuphane_otomasyonu.dto.LoginRequest;
import kutuphane.kutuphane_otomasyonu.model.Users;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersRepostory userRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UsersRepostory userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody LoginRequest request) {

    Users user = userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Kullanıcı yok"));

    if (!user.getPassword().equals(request.getPassword())) {
        throw new RuntimeException("Şifre yanlış");
    }

    String token = jwtUtil.generateToken(user); // email yerine user gönderiyoruz

    // Opsiyonel: JSON ile token ve rolü dön
    return ResponseEntity.ok(Map.of(
            "token", token,
            "role", user.getRole()
    ));
}
}

