package jobis.jobisimpTask.repository;


import jobis.jobisimpTask.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    boolean existsByUserId(String userId);
    MemberEntity findByUserId(String userId);
}
