package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LaboratoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("lab_id")
    private int labId;

}
