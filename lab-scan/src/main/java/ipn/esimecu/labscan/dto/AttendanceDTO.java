package ipn.esimecu.labscan.dto;


public class AttendanceDTO extends StudentDTO  {
    
@Annotation("attendance_id")
private int AttendanceId;
private String observations;
private boolean attendance;
}
