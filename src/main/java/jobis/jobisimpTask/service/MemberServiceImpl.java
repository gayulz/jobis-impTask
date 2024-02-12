package jobis.jobisimpTask.service;

import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.repository.UserdataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserdataRepository userdataRepository;

    public boolean saveMember(MemberDto memberDto) {
        // USERDATA 테이블 검증
        boolean existsInUserData = userdataRepository.existsByRegNo(memberDto.getRegNo());
        // MEMBER 테이블 검증
        boolean existsInMember = memberRepository.existsByUserId(memberDto.getUserId());

        if (existsInUserData && !existsInMember) {
            memberRepository.save(memberDto); // 회원 정보 저장
            return true;
        }
        return false;
    }
}
