package ipn.esimecu.labscan.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransferRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty("from_student_id")
    private int fromStudentId;

    @JsonProperty("to_student_id")
    private int toStudentId;

}
