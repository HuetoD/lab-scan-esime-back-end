package ipn.esimecu.labscan.dto.request;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import ipn.esimecu.labscan.dto.AdminDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LoginRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Email(message = "El correo debe ser valido")
    private String email;

    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;
}