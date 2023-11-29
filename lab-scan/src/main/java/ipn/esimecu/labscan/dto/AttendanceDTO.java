package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttendanceDTO extends StudentDTO  {
    
@JsonProperty("attendance_id")
private int AttendanceId;
private String observations;
private boolean attendance;
}
