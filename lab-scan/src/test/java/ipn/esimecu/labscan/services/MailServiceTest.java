package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.service.MailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class MailServiceTest {

    private final MailService mailService;

    @Autowired
    public MailServiceTest(MailService mailService) {
        this.mailService = mailService;
    }

    @Test
    @Order(1)
    public void sendSyncEmailTest() {
        try {
            mailService.sendSyncNewPassword("xorore9923@anawalls.com", "campanillazo");
        }catch(Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
