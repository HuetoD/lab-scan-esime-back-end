package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.AttendanceBaseDTO;
import ipn.esimecu.labscan.entity.AttendanceEntity;
import ipn.esimecu.labscan.entity.StudentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AttendanceMapper implements SuperMapper<AttendanceEntity, AttendanceBaseDTO> {

    private final StudentMapper studentMapper;

    @Override
    public AttendanceEntity map(AttendanceBaseDTO attendanceDTO) {
        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setAttendance(attendanceDTO.isAttendance());
        attendance.setObservation(attendanceDTO.getObservations());
        attendance.setDate(LocalDateTime.now());
        return attendance;
    }

    @Override
    public AttendanceBaseDTO map(AttendanceEntity entity) {
        AttendanceBaseDTO response = map(entity.getStudent());
        response.setAttendanceId(entity.getAttendanceId());
        response.setSubjectId(entity.getSubject().getSubjectId());
        response.setObservations(entity.getObservation());
        response.setAttendance(entity.isAttendance());
        return response;
    }

    public AttendanceBaseDTO map(StudentEntity student) {
        return (AttendanceBaseDTO) studentMapper.map(student);
    }
}
