package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.RoleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleUserRepository extends JpaRepository<RoleUserEntity, Integer> {
}