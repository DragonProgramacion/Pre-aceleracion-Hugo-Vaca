package com.alkemy.disney.services.imp;

import com.alkemy.disney.services.EmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailServiceImp implements EmailService {

    @Autowired
    private Environment environment;

    @Value("${alkemy.disney.email.sender}")
    private String emailSender;

    public void sendWelcomeEmailTo(String to) {

        String apiKey = environment.getProperty("EMAIL_API_KEY");

        Email fromEmail = new Email(emailSender);
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", "Welcome to Alkemy disney, enjoy the Disney World experience!");

        String subject = "Alkemy Disney";

        Mail mail = new Mail(fromEmail, subject, toEmail, content);
        SendGrid sendGrid = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);

        } catch (IOException ex) {
            System.out.println("Email sending error");
        }
    }
}
