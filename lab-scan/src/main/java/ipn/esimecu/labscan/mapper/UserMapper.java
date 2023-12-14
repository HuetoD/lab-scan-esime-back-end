package ipn.esimecu.labscan.mapper;

import ipn.esimecu.labscan.dto.response.AdminResponse;
import ipn.esimecu.labscan.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements SuperMapper<UserEntity, AdminResponse> {

    @Override
    public UserEntity map(AdminResponse adminResponse) {
        return null;
    }

    @Override
    public AdminResponse map(UserEntity entity) {
        AdminResponse adminResponse = new AdminResponse();
        adminResponse.setAdminId(entity.getUserId());
        adminResponse.setPassword("");
        adminResponse.setEmail(entity.getEmail());
        return adminResponse;
    }
}
