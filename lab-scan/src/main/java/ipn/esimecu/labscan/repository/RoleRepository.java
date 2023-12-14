package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.RoleEntity;
import ipn.esimecu.labscan.entity.constant.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRoleName(Role role);

}
