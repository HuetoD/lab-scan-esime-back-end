package ipn.esimecu.labscan.dto.response;

import java.time.LocalDate;
import ipn.esimecu.labscan.dto.Annotation;

public class SemesterResponse {
    @Annotation ("semester_id")
    private int SemesterId;
    private String name;
    @Annotation ("start_date")
    private LocalDate StartDate;
    @Annotation ("end_date")
    private LocalDate EndDate;
    
}
