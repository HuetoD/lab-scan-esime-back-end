package ipn.esimecu.labscan.dto.response;

import ipn.esimecu.labscan.dto.Annotation;
import ipn.esimecu.labscan.dto.AttendanceDTO;

public class AttendanceResponse extends AttendanceDTO  {
    
@Annotation ("teacher_full_name")
private String TeacherFullName;
@Annotation ("group_name")
private String GroupName;
@Annotation ("laboratory_name")
private String LaboratoryName;
private String when;
@Annotation ("ws_code")
private String WsCode;
}