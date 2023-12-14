package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@Disabled
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class CommonServiceTest {

    private final CommonService commonService;

    @Autowired
    public CommonServiceTest(final CommonService commonService) {
        this.commonService = commonService;
    }

    @Test
    @Order(1)
    public void semesterNonEmptyListTest() {
        Assertions.assertEquals(false, commonService.getSemesters().isEmpty());
    }

    @Test
    @Order(2)
    public void labsNonEmptyList() {
        Assertions.assertEquals(false, commonService.getLabs().isEmpty());
    }

    @Test
    @Order(3)
    public void getOnly6CV15OnTuesdayTest() {
        GroupDTO expected = GroupDTO.builder()
                                    .subjectId(2)
                                    .groupName("6CV15 - Lab. Computación 1")
                                    .build();
        List<GroupDTO> result = commonService.getGroups("Lab. Computación 1", 24, LocalDate.of(2023, 12, 1));
        Assertions.assertNotNull(result.get(0));
        Assertions.assertEquals(expected, result.get(0));
        log.info(result.get(0).toString());
    }

    @Test
    @Order(4)
    public void identifiersEmptyTest() {
        Assertions.assertEquals(true, commonService.loadAllIdentifiers().isEmpty());
    }

}
