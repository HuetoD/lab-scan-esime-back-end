package ipn.esimecu.labscan.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Annotation ("student_id")
    private int StudentId; 
    @Annotation ("student_identification_type")
    private String StudentIdentificationType;
    @Annotation ("student_report_number")
    private String StudentReportNumber;
    @Annotation ("student_pc_number")
    private String StudentPcNumber;
    @Annotation ("student_qr_code")
    private String StudentQrCode;

}
