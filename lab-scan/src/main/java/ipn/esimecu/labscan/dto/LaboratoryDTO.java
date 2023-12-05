package ipn.esimecu.labscan.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LaboratoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty ("lab_id")
    private int labId;
    @JsonProperty ("lab_name")
    private String labName;
}
