package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectLabNameResultDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public SubjectLabNameResultDTO(SubjectLabNameResultDTO another) {
        this(another.subjectLabId, another.subjectId, another.groupName, another.labName);
    }

    public SubjectLabNameResultDTO(int subjectLabId, int subjectId, String groupName) {
        this.subjectLabId = subjectLabId;
        this.subjectId = subjectId;
        this.groupName = groupName;
    }

    @JsonProperty("subject_lab_id")
    private int subjectLabId;

    @JsonProperty("subject_id")
    private int subjectId;

    @JsonProperty("group_name")
    private String groupName;

    @JsonProperty("lab_name")
    private String labName;

}
