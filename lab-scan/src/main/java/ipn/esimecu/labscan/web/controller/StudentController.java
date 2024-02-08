package ipn.esimecu.labscan.web.controller;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.LaboratoryDTO;
import ipn.esimecu.labscan.dto.StudentBaseDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import ipn.esimecu.labscan.dto.SubjectLabNameResultDTO;
import ipn.esimecu.labscan.dto.request.TransferRequest;
import ipn.esimecu.labscan.dto.response.SemesterResponse;
import ipn.esimecu.labscan.service.CommonService;
import ipn.esimecu.labscan.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("student")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    private final CommonService commonService;

    @Autowired
    public StudentController(StudentService studentService, CommonService commonService) {
        this.studentService = studentService;
        this.commonService = commonService;
    }

    @GetMapping("ping")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok("Pong :: Student :: " + LocalDateTime.now());
    }

    @GetMapping("get-labs")
    public ResponseEntity<List<LaboratoryDTO>> getLabs() {
        return ResponseEntity.ok(commonService.getLabs());
    }

    @GetMapping("get-semesters")
    public ResponseEntity<List<SemesterResponse>> getSemesters() {
        return ResponseEntity.ok(commonService.getSemesters());
    }

    @GetMapping("get-groups")
    public ResponseEntity<List<GroupDTO>> getGroups(@RequestParam String laboratory,
                                                    @RequestParam int semester,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(commonService.getGroups(laboratory, semester, date));
    }

    @GetMapping("get-groups-of-the-week")
    public ResponseEntity<List<GroupDTO>> getGroupsOfTheWeek(@RequestParam String laboratory,
                                                             @RequestParam int semester) {
        return ResponseEntity.ok(commonService.getGroupsOfTheWeek(laboratory, semester));
    }

    @GetMapping("get-groups-by-semester/{semester}")
    public ResponseEntity<List<SubjectLabNameResultDTO>> getGroupsBySemester(@PathVariable int semester) {
        return ResponseEntity.ok(commonService.getGroupsBySemester(semester));
    }

    @GetMapping("load-all-identifiers")
    public ResponseEntity<List<String>> getIdentifiers() {
        return ResponseEntity.ok(commonService.loadAllIdentifiers());
    }


    @GetMapping("find-student-by-qr")
    public ResponseEntity<StudentDTO> findStudent(@RequestParam("qr_code") String qrCode) {
        return ResponseEntity.ok(studentService.findStudent(qrCode));
    }

    @GetMapping("find-student-by-report-number")
    public ResponseEntity<StudentDTO> findStudentByReportNumber(@RequestParam String reportNumber) {
        return ResponseEntity.ok(studentService.findStudentByReportNumber(reportNumber));
    }

    @PostMapping("save-student")
    public ResponseEntity<StudentBaseDTO> saveStudent(@RequestBody @Valid StudentDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(studentService.save(request));
    }

    @PutMapping("update-student")
    public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDTO request) {
        studentService.update(request);
        return ResponseEntity.ok("Se ha actualizado al estudiante: ".concat(request.getStudentFullName()));
    }

    @PatchMapping("transfer-student")
    public ResponseEntity<String> transferStudent(@RequestBody TransferRequest request) {
        studentService.transfer(request.getFromStudentId(), request.getToStudentId());
        return ResponseEntity.ok("Dos estudiantes han intercambiado de computadora");
    }

    @GetMapping("get-all-students-of-subjects")
    public void getAllStudentsOfSubject() {
        studentService.getAllStudentsOfSubject();
    }
}
