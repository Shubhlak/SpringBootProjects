package com.SR.PoleAppBackend.Service;

import com.SR.PoleAppBackend.DTO.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachment(EmailDetails details);
}