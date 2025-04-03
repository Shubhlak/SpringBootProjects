package com.SR.PoleAppBackend.Service;

import com.SR.PoleAppBackend.DTO.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

import java.io.File;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setSubject(details.getSubject());
            mailMessage.setText(details.getMsgBody());

            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while Sending Mail";
        }
    }

    @Override
    public String sendMailWithAttachment(EmailDetails details) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setSubject(details.getSubject());
            mimeMessageHelper.setText(details.getMsgBody());


            if (details.getAttachment() != null && !details.getAttachment().isEmpty()) {
                FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }

            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while sending mail!!!";
        }
    }
}