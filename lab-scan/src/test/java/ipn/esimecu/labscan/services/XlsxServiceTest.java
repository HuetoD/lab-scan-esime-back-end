package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.service.XlsxService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class XlsxServiceTest {

    private final XlsxService xlsxService;

    @Autowired
    public XlsxServiceTest(XlsxService xlsxService) {
        this.xlsxService = xlsxService;
    }

    @Test
    @Order(1)
    public void generateXlsxTest() {
        Assertions.assertDoesNotThrow(() -> xlsxService.generateXlsx(null));
    }

}
