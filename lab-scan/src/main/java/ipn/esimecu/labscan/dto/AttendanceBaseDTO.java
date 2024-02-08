package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceBaseDTO extends StudentBaseDTO implements Cloneable {
    
    @JsonProperty("attendance_id")
    private int attendanceId;

    @JsonProperty("subject_lab_id")
    private int subjectLabId;

    private String observations;

    private boolean attendance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AttendanceBaseDTO that = (AttendanceBaseDTO) o;

        if (attendanceId == that.attendanceId) return true;

        return subjectLabId == that.subjectLabId && super.equals(o);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + attendanceId;
        result = 31 * result + subjectLabId;
        return result + super.hashCode();
    }

    @Override
    public AttendanceBaseDTO clone() {
        AttendanceBaseDTO base = new AttendanceBaseDTO(attendanceId, subjectLabId, observations, attendance);
        base.setStudentId(getStudentId());
        base.setStudentFullName(getStudentFullName());
        base.setStudentQrCode(getStudentQrCode());
        base.setStudentPcNumber(getStudentPcNumber());
        base.setStudentReportNumber(getStudentReportNumber());
        base.setStudentIdentificationType(getStudentIdentificationType());
        return base;
    }
}
