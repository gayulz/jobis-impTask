package jobis.jobisimpTask.repository;

import jobis.jobisimpTask.dto.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserdataRepository extends JpaRepository<UserData, Long> {
    public boolean existsByRegNo(String regNo);
}
