package ipn.esimecu.labscan.dto.response;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonPropertyOrder(alphabetic = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AdminResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty ("admin_id")
    private int adminId;

    private String email;

    private String password;
}
