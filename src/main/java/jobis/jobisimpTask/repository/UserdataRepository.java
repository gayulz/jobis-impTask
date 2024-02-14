package jobis.jobisimpTask.repository;

import jobis.jobisimpTask.entity.UserDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserdataRepository extends JpaRepository<UserDataEntity, Long> {
    public boolean existsByRegNo(String regNo);
}
