package ipn.esimecu.labscan.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ipn.esimecu.labscan.dto.AttendanceBaseDTO;
import ipn.esimecu.labscan.dto.request.AttendanceFiltersRequest;
import ipn.esimecu.labscan.dto.response.AttendanceResponse;
import ipn.esimecu.labscan.service.AdminService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("generate_template")
    public ResponseEntity<AttendanceResponse> generateTemplate(@RequestParam("subject_lab_id") int subjectLabId) {
        return ResponseEntity.ok(adminService.generateTemplate(subjectLabId));
    }

    @GetMapping("find-attendances-by-filters")
    public ResponseEntity<List<AttendanceBaseDTO>> findAttendancesByFilters(@RequestParam int semester,
                                                                            @RequestParam("subject_lab_id") int subjectLabId,
                                                                            @RequestParam("like_student_name") String likeStudentName,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        return ResponseEntity.ok(adminService.findAttendancesByFilters(AttendanceFiltersRequest.builder()
                                                                                                .semester(subjectLabId)
                                                                                                .subjectLabId(subjectLabId)
                                                                                                .likeStudentName(likeStudentName)
                                                                                                .start(start)
                                                                                                .end(end)
                                                                                                .build()));
    }



}
