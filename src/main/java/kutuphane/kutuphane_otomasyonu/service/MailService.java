package kutuphane.kutuphane_otomasyonu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender; // Gerçek mail göndericiyi enjekte et

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to); // Alıcı adresi şart!
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message); // Gerçek gönderim komutu
        System.out.println("Mail başarıyla gönderildi: " + to);
    }
}
