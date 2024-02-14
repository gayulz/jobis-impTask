package jobis.jobisimpTask.repository;


import jobis.jobisimpTask.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    boolean existsByUserId(String userId);
    List<MemberEntity> findByUserId(String userId);
}
