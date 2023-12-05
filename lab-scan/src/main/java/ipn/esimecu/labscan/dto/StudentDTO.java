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

public class StudentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonProperty ("student_id")
    private int studentId; 
    @JsonProperty ("student_identification_type")
    private String studentIdentificationType;
    @JsonProperty ("student_report_number")
    private String studentReportNumber;
    @JsonProperty ("student_pc_number")
    private String studentPcNumber;
    @JsonProperty ("student_qr_code")
    private String studentQrCode;

}
