package ipn.esimecu.labscan.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import ipn.esimecu.labscan.dto.SubjectLabNameResultDTO;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class FilteredAttendanceResponse extends SubjectLabNameResultDTO implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 1L;

    public FilteredAttendanceResponse(FilteredAttendanceResponse another) {
        this(another.getSubjectLabId(), another.getSubjectId(), another.getGroupName(), another.getLabName(), another.getTeacherFullName(), null);
        this.date = another.getDate();
    }

    public FilteredAttendanceResponse(int subjectLabId, int subjectId, String groupName, String labName, String teacherFullName, LocalDateTime date) {
        super(subjectLabId, subjectId, groupName, labName);
        this.teacherFullName = teacherFullName;
        this.date = Optional.ofNullable(date).map(LocalDateTime::toLocalDate).orElse(null);
    }

    @JsonProperty("teacher_full_name")
    private String teacherFullName;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Override
    public FilteredAttendanceResponse clone() {
        return new FilteredAttendanceResponse(this);
    }
}