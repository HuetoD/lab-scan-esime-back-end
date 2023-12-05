package ipn.esimecu.labscan.dto.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipn.esimecu.labscan.dto.GroupDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class StudentRequest {
    @JsonProperty ("sacadem_register_date")
    private LocalDate sacademRegisterDate;
    private  HashMap<String, ArrayList<GroupDTO>> groups;
    private String photo;
}
