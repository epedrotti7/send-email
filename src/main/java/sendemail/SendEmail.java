package sendemail;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@RestController
public class SendEmail {

    @PostMapping("/email")
    public void sendEmail(@RequestBody Email request) throws MessagingException {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected jakarta.mail.PasswordAuthentication getPasswordAuthentication() {
                return new jakarta.mail.PasswordAuthentication("SEU_EMAIL", "SUA_SENHA");
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(request.getFrom()));

        List<InternetAddress> recipientAddresses = request.getTo().stream()
                .map(to -> {
                    try {
                        return new InternetAddress(to);
                    } catch (AddressException e) {
                        System.err.println("Endereço de e-mail inválido: " + to);
                        return null;
                    }
                })
                .filter(address -> address != null)
                .collect(Collectors.toList());

        if (!recipientAddresses.isEmpty()) {
            message.setRecipients(Message.RecipientType.TO, recipientAddresses.toArray(InternetAddress[]::new));
            message.setSubject(request.getSubject());
            message.setText(request.getContent());

            Transport.send(message);
            System.out.println("Email enviado com sucesso!");
        } else {
            System.out.println("Nenhum endereço de e-mail válido fornecido.");
        }
    }
}
