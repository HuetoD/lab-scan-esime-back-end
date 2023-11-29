package ipn.esimecu.labscan.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


import ipn.esimecu.labscan.dto.AttendanceDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AttendanceResponse extends AttendanceDTO  {
    
@JsonProperty ("teacher_full_name")
private String TteacherFullName;
@JsonProperty ("group_name")
private String groupName;
@JsonProperty ("laboratory_name")
private String laboratoryName;
private String when;
@JsonProperty ("ws_code")
private String wsCode;
}