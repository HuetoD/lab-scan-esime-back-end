package ipn.esimecu.labscan.dto;

import ipn.esimecu.labscan.entity.AttendanceEntity;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import ipn.esimecu.labscan.entity.ScheduleEntity;
import ipn.esimecu.labscan.entity.StudentEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString

public class Data {

                public StudentEntity student;
                private LaboratoryEntity laboratory;
                private AttendanceEntity observation;
                private ScheduleEntity schedule;
                public void setAttendance(AttendanceEntity attendanceEntity) {
                }

    
}
