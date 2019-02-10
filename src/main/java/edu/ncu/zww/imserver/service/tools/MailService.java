package edu.ncu.zww.imserver.service.tools;

import edu.ncu.zww.imserver.common.util.EmailType;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

public interface MailService {
    public void sendMail(String to, EmailType Type) throws MessagingException;
}
