package ipn.esimecu.labscan.dto.response;

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



public class AdminResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty ("admin_id")
    private int adminId;
    private String email;
}
