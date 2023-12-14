package ipn.esimecu.labscan.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class EventGroupEsimecuResponse implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("tipo")
    private EsimeCuTypeEnumResponse type;

    @JsonProperty("v_a")
    private String subjectName;

    @JsonProperty("v_g")
    private String groupName;

    @JsonProperty("v_h")
    private int hoursPerWeek;

    @JsonProperty("v_l")
    private String monday;

    @JsonProperty("v_m")
    private String tuesday;

    @JsonProperty("v_x")
    private String wednesday;

    @JsonProperty("v_j")
    private String thursday;

    @JsonProperty("v_v")
    private String friday;

    @JsonProperty("v_d")
    private String teacherFullName;

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof EventGroupEsimecuResponse))
            return false;
        final var another = (EventGroupEsimecuResponse) o;
        return another != null
                && (another.getSubjectName().equals(subjectName)
                    || "LAB. ".concat(subjectName).equals(another.getSubjectName())
                    || "LAB. ".concat(another.getSubjectName()).equals(subjectName))
                    || "LAB. ".concat(subjectName).equals("LAB. ".concat(another.getSubjectName()));
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (subjectName != null ? subjectName.hashCode() : 0);
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        result = 31 * result + hoursPerWeek;
        result = 31 * result + (monday != null ? monday.hashCode() : 0);
        result = 31 * result + (tuesday != null ? tuesday.hashCode() : 0);
        result = 31 * result + (wednesday != null ? wednesday.hashCode() : 0);
        result = 31 * result + (thursday != null ? thursday.hashCode() : 0);
        result = 31 * result + (friday != null ? friday.hashCode() : 0);
        result = 31 * result + (teacherFullName != null ? teacherFullName.hashCode() : 0);
        return result;
    }

    @Override
    public EventGroupEsimecuResponse clone() {
        return EventGroupEsimecuResponse.builder()
                .type(type)
                .subjectName(subjectName)
                .groupName(groupName)
                .hoursPerWeek(hoursPerWeek)
                .monday(monday)
                .tuesday(tuesday)
                .wednesday(wednesday)
                .thursday(thursday)
                .friday(friday)
                .teacherFullName(teacherFullName)
                .build();
    }
}