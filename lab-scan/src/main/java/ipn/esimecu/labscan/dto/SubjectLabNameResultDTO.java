package ipn.esimecu.labscan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectLabNameResultDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int subjectId;

    private String groupName;

    private String labName;

}
