package ipn.esimecu.labscan.web.controller;

import ipn.esimecu.labscan.dto.LaboratoryDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

    private final StudentService studentService;

    @GetMapping("get-labs")
    public ResponseEntity<LaboratoryDTO> getLabs() {
        LaboratoryDTO laboratory = new LaboratoryDTO();
        laboratory.setLabName("Pong");
        laboratory.setLabId(117);
        return ResponseEntity.ok((laboratory));
    }

    @GetMapping("find-student")
    public ResponseEntity<StudentDTO> findStudent(@RequestParam("qr_code") String qrCode) {
        log.info("Peticion: ".concat(qrCode));
        return ResponseEntity.ok(studentService.findStudent(qrCode));
    }
}
