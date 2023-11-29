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

public class AttendanceDTO extends StudentDTO  {
    
@JsonProperty("attendance_id")
private int attendanceId;
private String observations;
private boolean attendance;
}
