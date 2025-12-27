package kutuphane.kutuphane_otomasyonu.Security;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // 1. ADIM: Filtrenin hiç çalışmaması gereken durumları belirle
   @Override
protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getServletPath();
    // Senin gerçek login yolun /api/users/login olduğu için burayı güncelliyoruz
    return path.equals("/api/users/login") || 
           path.equals("/api/users/register") ||
           path.startsWith("/auth/") || 
           path.endsWith(".html") || 
           path.endsWith(".js") || 
           path.startsWith("/css/") || 
           path.startsWith("/images/");
}

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        // 2. ADIM: Header kontrolü
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                // 3. ADIM: Token doğrulama
                if (jwtUtil.validateToken(token)) {
                    String email = jwtUtil.extractEmail(token);
                    String roleFromToken = jwtUtil.extractRole(token);
                    String role = "ROLE_" + roleFromToken;

                    List<GrantedAuthority> authorities =
                            List.of(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email, null, authorities
                            );

                    // Spring Security bağlamına kullanıcıyı yerleştir
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // Token geçersizse veya süresi dolmuşsa burada hata basabiliriz
                System.out.println("JWT Doğrulama Hatası: " + e.getMessage());
            }
        }

        // 4. ADIM: İsteği bir sonraki filtreye (veya Controller'a) gönder
        filterChain.doFilter(request, response);
    }
}