package ipn.esimecu.labscan.services;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.exception.DaeServiceException;
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
import java.util.concurrent.atomic.AtomicReference;

@Disabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class StudentServiceTest {

    private final StudentService studentService;

    private static StudentDTO studentRequest;

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

    @Test
    @Order(5)
    public void findStudentByQrCodeTest() {
        AtomicReference<StudentDTO> response = new AtomicReference<>(null);
        final String qrCode = "515248ec6a4ac952444c9493b4e1fabd3e28be4789b8fab291523177ecec5";
        Assertions.assertDoesNotThrow(() -> response.set(studentService.findStudent(qrCode)));
        log.info(response.get().toString());
    }

    @Test
    @Order(6)
    public void findStudentWithWrongQrCodeTest() {
        final String qrCode = "fbb613cb862c2cac2546e356b42943b3c9d53bdbefd9e4fc9deb62589f0c";//9";
        Assertions.assertThrows(DaeServiceException.class, () -> studentService.findStudent(qrCode));
    }

    @BeforeAll
    public static void setup() {
        final Map<String, List<GroupDTO>> groups = new HashMap<>();
        studentRequest = new StudentDTO();
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
