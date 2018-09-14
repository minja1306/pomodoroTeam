package eu.execom.pomodoroTeam.services;

import eu.execom.pomodoroTeam.entities.Invitation;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class MailService {

    private JavaMailSender javaMailSender;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMail(String to, String from, String subject, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeHelper = new MimeMessageHelper(message, false, CharEncoding.UTF_8);
        mimeHelper.setFrom(from);
        mimeHelper.setTo(to);
        mimeHelper.setSubject(subject);
        mimeHelper.setText(content, false);

        javaMailSender.send(message);
    }

    public void sendInvitationMail(String fromWhichUser, Invitation invitation) {
        Context context = new Context();
        context.setVariable("fromWhichUser", fromWhichUser);
        context.setVariable("invitation", invitation);

        String content = templateEngine.process("sendInvitation", context);
        try {
            sendMail(invitation.getUsername(), "bojana", "link to activate invitation", content);
        } catch (MessagingException e) {
            log.error("something bad", e);
        }
    }
}


