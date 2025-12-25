package kutuphane.kutuphane_otomasyonu.Security;

import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kutuphane.kutuphane_otomasyonu.model.Users;

@Component
public class JwtUtil {

    private final String SECRET_KEY =
            "mihrace-kutuphane-otomasyonu-jwt-secret-key-256bit!";

    // TOKEN OLUŞTURMA
    public String generateToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole()) // ROLE_USER / ROLE_ADMIN
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()),
                          SignatureAlgorithm.HS256)
                .compact();
    }

    // EMAIL OKU
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // ROLE OKU
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // TOKEN GEÇERLİ Mİ?
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    //  SÜRESİ DOLMUŞ MU?
    public boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }

    //  GENERIC CLAIM OKUYUCU
    public <T> T extractClaim(String token,
                              Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //  TÜM CLAIM'LER
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
