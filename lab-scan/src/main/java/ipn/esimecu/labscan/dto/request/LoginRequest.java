package ipn.esimecu.labscan.dto.request;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private String email;
    private String password;
}
