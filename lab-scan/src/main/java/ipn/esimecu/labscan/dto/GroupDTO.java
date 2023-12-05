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

public class GroupDTO {
    @JsonProperty ("group_id")
    private int groupId;
    @JsonProperty ("group_name")
    private String groupNombre;
}
