package kutuphane.kutuphane_otomasyonu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
     public void sendMail(String subject, String text) {
        System.out.println("MAIL (mock): " + subject + " - " + text);
    }
}
