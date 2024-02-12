package jobis.jobisimpTask.service;

import jobis.jobisimpTask.crypto.Password;
import jobis.jobisimpTask.crypto.ResidentRegistration;
import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.repository.UserdataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserdataRepository userdataRepository;

    public boolean saveMember(MemberDto memberDto) {
        // USERDATA 테이블 검증
        boolean existsInUserData = userdataRepository.existsByRegNo(memberDto.getRegNo());
        // MEMBER 테이블 검증
        boolean existsInMember = memberRepository.existsByUserId(memberDto.getUserId());
        try {
            if (existsInUserData && !existsInMember) {
                String encryptedPassword = Password.encrypt(memberDto.getPassword());
                memberDto.setPassword(encryptedPassword);
                String encryptRegistration = ResidentRegistration.encrypt(memberDto.getRegNo());
                memberDto.setRegNo(encryptRegistration);
                memberRepository.save(memberDto); // 회원 정보 저장
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
