package ipn.esimecu.labscan.dto;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class StudentBaseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonProperty ("student_id")
    private int studentId;

    @JsonProperty ("student_identification_type")
    @NotNull
    @NotEmpty
    private String studentIdentificationType;

    @JsonProperty ("student_report_number")
    private String studentReportNumber;

    @JsonProperty("student_full_name")
    @NotNull
    @NotEmpty
    private String studentFullName;

    @JsonProperty ("student_pc_number")
    @NotNull
    @NotEmpty
    private String studentPcNumber;

    @JsonProperty ("student_qr_code")
    private String studentQrCode;

}
