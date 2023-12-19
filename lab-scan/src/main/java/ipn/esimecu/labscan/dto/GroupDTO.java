package ipn.esimecu.labscan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
public class GroupDTO implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    public GroupDTO(GroupDTO another) {
        this(another.subjectLabId, another.subjectId, another.groupName);
    }

    @JsonProperty("subject_lab_id")
    private int subjectLabId;

    @JsonProperty ("subject_id")
    private int subjectId;

    @JsonProperty ("group_name")
    private String groupName;

    @Override
    public GroupDTO clone() {
        return GroupDTO.builder()
                    .subjectLabId(subjectLabId)
                    .subjectId(subjectId)
                    .groupName(groupName)
                    .build();
    }
}
