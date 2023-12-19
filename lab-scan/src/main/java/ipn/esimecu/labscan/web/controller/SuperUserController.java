package ipn.esimecu.labscan.web.controller;

import ipn.esimecu.labscan.dto.AdminDTO;
import ipn.esimecu.labscan.dto.response.AdminResponse;
import ipn.esimecu.labscan.service.SuperUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("superuser")
public class SuperUserController {

    private final SuperUserService superUserService;

    @Autowired
    public SuperUserController(SuperUserService superUserService) {
        this.superUserService = superUserService;
    }

    @GetMapping("ping")
    public ResponseEntity<String> pong() {
        return ResponseEntity.ok("Pong :: Admin :: ".concat(LocalDateTime.now().toString()));
    }

    @GetMapping("load-admins")
    public ResponseEntity<List<AdminResponse>> loadAdmins() {
        return ResponseEntity.ok(superUserService.loadAdmins());
    }

    @PostMapping("add-admin")
    public ResponseEntity<AdminResponse> addAdmin(@RequestBody @Valid AdminDTO adminDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                            .body(superUserService.addAdmin(adminDTO));
    }

    @PutMapping("update-admin")
    public ResponseEntity<AdminResponse> updateAdmin(@RequestBody @Valid AdminDTO request) {
        return ResponseEntity.ok(superUserService.updateAdmin(request));
    }

    @DeleteMapping("remove-admin/{adminId}")
    public void deleteAdmin(@PathVariable int adminId) {
        superUserService.removeAdmin(adminId);
    }

}
