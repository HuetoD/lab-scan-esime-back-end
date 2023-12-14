package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipn.esimecu.labscan.dto.request.LoginRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class AdminDTO extends LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("admin_id")
    private int adminId;

}
