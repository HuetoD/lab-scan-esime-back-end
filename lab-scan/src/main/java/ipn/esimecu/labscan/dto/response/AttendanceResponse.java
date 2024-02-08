package ipn.esimecu.labscan.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;


import com.fasterxml.jackson.annotation.JsonUnwrapped;
import ipn.esimecu.labscan.dto.AttendanceBaseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttendanceResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JsonUnwrapped
    private Collection<AttendanceBaseDTO> attendances;

    @JsonProperty ("teacher_full_name")
    private String teacherFullName;

    @JsonProperty ("group_name")
    private String groupName;

    @JsonProperty ("laboratory_name")
    private String laboratoryName;

    private String when;

    @JsonProperty ("ws_code")
    private String wsCode;
}