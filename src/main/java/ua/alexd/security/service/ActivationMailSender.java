package ua.alexd.security.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ua.alexd.security.User;

@Service
public class ActivationMailSender {
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String username;

    public ActivationMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendActivation(@NotNull User newUser) {
        var mailMessage = new SimpleMailMessage();

        var message = String.format(
                "Привіт, %s!\n" +
                "Станьте одним із привілейованих клієнтів Alex Laptop Shop!\n" +
                "Будь ласка, перейдіть за посиланням: http://localhost:8080/registration/activate/%s",
                newUser.getUsername(), newUser.getActivationCode()
        );

        mailMessage.setFrom(username);
        mailMessage.setTo(newUser.getEmail());
        mailMessage.setSubject("Активація аккаунту");
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}