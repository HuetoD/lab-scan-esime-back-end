package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.LaboratoryDTO;
import ipn.esimecu.labscan.entity.LaboratoryEntity;
import org.springframework.stereotype.Component;

@Component
public class LaboratoryMapper implements SuperMapper<LaboratoryEntity, LaboratoryDTO> {

    @Override
    public LaboratoryEntity map(LaboratoryDTO laboratoryDTO) {
        return null;
    }

    @Override
    public LaboratoryDTO map(LaboratoryEntity entity) {
        LaboratoryDTO laboratoryDTO = new LaboratoryDTO();
        laboratoryDTO.setLabId(entity.getLaboratoryId());
        laboratoryDTO.setLabName(entity.getName());
        return laboratoryDTO;
    }
}
