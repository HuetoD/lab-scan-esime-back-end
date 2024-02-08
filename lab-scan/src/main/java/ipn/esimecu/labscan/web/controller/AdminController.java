package ipn.esimecu.labscan.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ipn.esimecu.labscan.dto.response.FilteredAttendanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("create-attendance")
    public ResponseEntity<AttendanceBaseDTO> createAttendance(@RequestBody AttendanceBaseDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.createAttendance(request));
    }

    @PutMapping("update-attendance")
    public ResponseEntity<String> updateAttendance(@RequestBody AttendanceBaseDTO request) {
        adminService.updateAttendance(request);
        return ResponseEntity.ok("Se ha actualizado la asistencia de " + request.getStudentFullName());
    }

    @GetMapping("find-attendances-by-filters")
    public Map<String, List<AttendanceBaseDTO>> findAttendancesByFilters(@RequestParam int semester,
                                                                         @RequestParam(name = "subject_id", defaultValue = "0", required = false) int subjectId,
                                                                         @RequestParam(name = "subject_lab_id", defaultValue = "0", required = false) int subjectLabId,
                                                                         @RequestParam(name = "like_student_name", required = false) String likeStudentName,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        final ObjectMapper jsonMapper = new ObjectMapper();
        return adminService.findAttendancesByFilters(AttendanceFiltersRequest.builder()
                        .semester(semester)
                        .subjectId(subjectId)
                        .subjectLabId(subjectLabId)
                        .likeStudentName(likeStudentName)
                        .start(start)
                        .end(end)
                        .build())
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> {
                    try {
                        return jsonMapper.writeValueAsString(entry.getKey());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }, Map.Entry::getValue));
    }


}
