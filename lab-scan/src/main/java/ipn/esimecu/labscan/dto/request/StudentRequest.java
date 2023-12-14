package ipn.esimecu.labscan.dto.request;

import java.io.Serial;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipn.esimecu.labscan.dto.GroupDTO;

import ipn.esimecu.labscan.dto.StudentDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentRequest extends StudentDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty ("sacadem_register_date")
    private LocalDate sacademRegisterDate;

    @NotNull
    @NotEmpty
    private Map<String, List<GroupDTO>> groups;

    private String photo;

    @JsonProperty("semester_id")
    private int semesterId;
}
