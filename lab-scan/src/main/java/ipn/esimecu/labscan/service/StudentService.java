package ipn.esimecu.labscan.service;

import ipn.esimecu.labscan.dto.GroupDTO;
import ipn.esimecu.labscan.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final DaeService daeService;

    public StudentDTO findStudent(String qrCode) {
        //find with repo
        //else with dae and save student
        return daeService.findStudent(qrCode);
    }

    public List<GroupDTO> getGroups() {

        return null;
    }

}
