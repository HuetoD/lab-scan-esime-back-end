package ipn.esimecu.labscan.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import ipn.esimecu.labscan.dto.Annotation;
import ipn.esimecu.labscan.dto.GroupDTO;

public class StudentRequest {
    @Annotation ("sacadem_register_date")
    private LocalDate SacademRegisterDate;
    private  HashMap<String, ArrayList<GroupDTO>> groups;
    private String photo;
}
