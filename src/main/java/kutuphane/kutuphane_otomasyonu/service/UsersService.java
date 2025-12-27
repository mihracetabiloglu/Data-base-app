package kutuphane.kutuphane_otomasyonu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kutuphane.kutuphane_otomasyonu.Repository.UsersRepostory;
import kutuphane.kutuphane_otomasyonu.model.Users;

@Service
public class UsersService {

    private final UsersRepostory userRepository;

    public UsersService(UsersRepostory userRepository) {
        this.userRepository = userRepository;
    }

    //  KULLANICI KAYIT (NORMAL USER)
    public Users registerUser(Users user) {

        // Email kontrolü
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı");
        }

        user.setRole("USER"); // default rol
        return userRepository.save(user);
    }

    //  ADMIN KAYIT
    public Users registerAdmin(Users admin) {

        if (userRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Bu email zaten kayıtlı");
        }

        admin.setRole("ADMIN");
        return userRepository.save(admin);
    }

    //  KULLANICI GİRİŞ (LOGIN)
    public Users login(String email, String password) {

        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Şifre yanlış");
        }

        return user;
    }

    //  TÜM KULLANICILARI GETİR (ADMIN)
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    //  ID'YE GÖRE KULLANICI
    public Users getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    //  KULLANICI SİL (ADMIN)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
