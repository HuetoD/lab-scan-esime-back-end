package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.service.AdminService;
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

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class AdminServiceTest {

    private final AdminService adminService;

    @Autowired
    public AdminServiceTest(AdminService adminService) {
        this.adminService = adminService;
    }

    @Test
    @Order(1)
    public void findAttendancesByFiltersTest() {
        final AttendanceFiltersRequest filters = AttendanceFiltersRequest.builder()
                                                                         .semester(24)
                                                                         .subjectLabId(3)
                                                                         .likeStudentName(null)
                                                                         .start(LocalDate.now().minusMonths(3))
                                                                         .end(LocalDate.now())
                                                                         .build();
        Assertions.assertDoesNotThrow(() -> adminService.findAttendancesByFilters(filters));
    }

}
