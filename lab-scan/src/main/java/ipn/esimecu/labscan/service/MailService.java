package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.exception.UnsentMailException;
import ipn.esimecu.labscan.web.Config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.exceptions.TemplateProcessingException;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MailService {

    private final TemplateEngine templateEngine;

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    private final String SUBJECT = "Lab Scan Esime - Solicitud de cambio de contraseña";

    public String sendSyncNewPassword(String email, String newPassword) throws UnsentMailException {
        return sendNewPassword(email, newPassword);
    }

    @Async(Config.TASK_EXECUTOR_DEFAULT_BEAN_NAME)
    public CompletableFuture<String> sendAsyncNewPassword(String email, String newPassword) throws UnsentMailException {
        return CompletableFuture.supplyAsync(() -> sendNewPassword(email, newPassword));
    }

    protected String sendNewPassword(String email, String newPassword) throws UnsentMailException {
        try {
            final String html = loadTemplate(newPassword);
            MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
                helper.setFrom(from);
                helper.setTo(email);
                helper.setSubject(SUBJECT);
                helper.setText(html, true);
            };
            javaMailSender.send(mimeMessagePreparator);
            return newPassword;
        }catch (TemplateProcessingException tex) {
            throw new UnsentMailException("El template de email no ha sido cargado", tex);
        }catch (MailException mex) {
            mex.printStackTrace(System.err);
            throw new UnsentMailException("No se ha podido enviar el correo", mex);
        }
    }

    private String loadTemplate(String newPassword) throws TemplateProcessingException {
        Context context = new Context();
        context.setVariable("password", newPassword);
        return templateEngine.process("reset-password-email", context);
    }


}
