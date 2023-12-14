package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.request.StudentRequest;
import ipn.esimecu.labscan.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class StudentServiceTest {

    private final StudentService studentService;

    private static StudentRequest studentRequest;

    @Autowired
    public StudentServiceTest(StudentService studentService) {
        this.studentService = studentService;
    }

    @Disabled("because this has already been tested")
    @Test
    @Order(1)
    public void saveStudentServiceWithExistingSubjectTest() {
       Assertions.assertDoesNotThrow(() -> studentService.save(studentRequest));
    }

    @Disabled("because this has already been tested")
    @Test
    @Order(2)
    public void saveStudentWithNewSubjectTest() {
        Assertions.assertDoesNotThrow(() -> studentService.save(studentRequest));
    }

    @Test
    @Order(3)
    public void saveStudentWithDuplicatedDataTest() {
        Assertions.assertThrows(RuntimeException.class, () -> studentService.save(studentRequest))
                  .printStackTrace(System.err);
    }

    @Test
    @Order(4)
    public void updateStudentTest() {
        Assertions.assertDoesNotThrow(() -> studentService.update(studentRequest));
    }

    @BeforeAll
    public static void setup() {
        final Map<String, List<GroupDTO>> groups = new HashMap<>();
        studentRequest = new StudentRequest();
        studentRequest.setStudentId(6);
        studentRequest.setStudentPcNumber("24");
        studentRequest.setStudentQrCode("fbb613cb862c2cac2546e356b42943b3c9d53bdbefd9e4fc9deb62589f0c9");
        studentRequest.setSacademRegisterDate(null);
        studentRequest.setStudentReportNumber("2020351372");
        studentRequest.setStudentIdentificationType("CREDENCIAL ESTUDIANTE");
        studentRequest.setStudentFullName("Kevin Miranda Sanchez");
        studentRequest.setSemesterId(24);
        studentRequest.setGroups(groups);

        groups.put("Lab. Micros", Arrays.asList(
                GroupDTO.builder()
                        .groupName("6CV22")
                        .build()
        ));
    }

}
