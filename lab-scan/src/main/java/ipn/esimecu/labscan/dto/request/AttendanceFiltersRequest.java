package ipn.esimecu.labscan.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttendanceFiltersRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int semester;

    private int subjectLabId;

    private String likeStudentName;

    private LocalDate start;

    private LocalDate end;

}
