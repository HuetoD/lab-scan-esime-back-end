package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}