package ipn.esimecu.labscan.dto.response;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class SemesterResponse implements Serializable {
    private static final long serialVersionUID = 1L; 
    @JsonProperty ("semester_id")
    private int semesterId;
    private String name;
    @JsonProperty ("start_date")
    private LocalDate startDate;
    @JsonProperty ("end_date")
    private LocalDate endDate;
    
}
