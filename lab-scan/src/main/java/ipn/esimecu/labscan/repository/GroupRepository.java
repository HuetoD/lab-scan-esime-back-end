package ipn.esimecu.labscan.repository;

import ipn.esimecu.labscan.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Integer> {

    Optional<GroupEntity> findByName(String name);

    List<GroupEntity> findByNameIn(Collection<String> names);

}