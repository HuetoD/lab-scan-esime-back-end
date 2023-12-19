package ipn.esimecu.labscan.dto;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipn.esimecu.labscan.dto.GroupDTO;

import ipn.esimecu.labscan.dto.StudentBaseDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"photo"})
public class StudentDTO extends StudentBaseDTO {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty ("sacadem_register_date")
    private LocalDate sacademRegisterDate;

    @NotNull
    @NotEmpty(message = "Debe incluir una lista de grupos")
    private Map<String, List<GroupDTO>> groups;

    private String photo;

    @JsonProperty("semester_id")
    @Positive(message = "No se ha incluido el semestre correctamente")
    private int semesterId;
}
