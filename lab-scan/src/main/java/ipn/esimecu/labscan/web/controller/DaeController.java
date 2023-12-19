package ipn.esimecu.labscan.web.controller;

import ipn.esimecu.labscan.dto.StudentBaseDTO;
import ipn.esimecu.labscan.service.DaeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dae")
@RequiredArgsConstructor
@CrossOrigin
public class DaeController {

    private final DaeService daeService;

    @GetMapping("find-student")
    public ResponseEntity<StudentBaseDTO> findStudent(@RequestParam("qr_code") String qrCode) {
        return ResponseEntity.ok(daeService.findStudent(qrCode));
    }

}
