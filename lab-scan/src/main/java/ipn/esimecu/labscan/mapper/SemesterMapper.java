package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.response.SemesterResponse;
import ipn.esimecu.labscan.entity.SemesterEntity;
import org.springframework.stereotype.Component;

@Component
public class SemesterMapper implements SuperMapper<SemesterEntity, SemesterResponse> {

    @Override
    public SemesterEntity map(SemesterResponse semesterResponse) {
        return null;
    }

    @Override
    public SemesterResponse map(SemesterEntity entity) {
        SemesterResponse response = new SemesterResponse();
        response.setSemesterId(entity.getSemesterId());
        response.setName("?");
        response.setStartDate(entity.getStartDate());
        response.setEndDate(entity.getEndDate());
        return response;
    }
}
