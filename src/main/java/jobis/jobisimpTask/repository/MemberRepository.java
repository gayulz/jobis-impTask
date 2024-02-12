package jobis.jobisimpTask.repository;


import jobis.jobisimpTask.dto.MemberDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberDto, Long> {
    boolean existsByUserId(String userId);
    List<MemberDto> findByUserId(String userId);
}
