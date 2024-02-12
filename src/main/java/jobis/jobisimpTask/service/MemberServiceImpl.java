package jobis.jobisimpTask.service;

import jobis.jobisimpTask.crypto.Password;
import jobis.jobisimpTask.crypto.ResidentRegistration;
import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.repository.UserdataRepository;
import jobis.jobisimpTask.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserdataRepository userdataRepository;
    @Autowired
    private JwtUtils jwtUtils;

    /*
    * 회원가입 서비스 로직
    * 1. USERDATA 테이블에 존재하는지 확인
    * 2. MEMBER 테이블에 존재하는지 확인
    * 3. USERDATA 테이블에 존재하고 MEMBER 테이블에 존재하지 않으면 회원가입 성공
    * 4. 회원가입 성공시 true 반환
    * 5. 회원가입 실패시 false 반환
    * 6. 회원가입 성공시 비밀번호 암호화
    * 7. 회원가입 성공시 주민등록번호 암호화
    * 8. 회원가입 성공시 회원정보 저장
    * */
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

    /*
    * 로그인 서비스 로직
    * 1. 사용자 인증
    * 2. 사용자 인증 성공시 JWT 토큰 발급
    * 3. JWT 토큰 반환
    * 4. 사용자 인증 실패시 null 반환
    * */
    @Override
    public String login(MemberDto memberDto) {
        MemberDto authenticatedMember = null;
        try {
            authenticatedMember = authenticateUser(memberDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (authenticatedMember != null) {
            return jwtUtils.generateJwtToken(authenticatedMember.getName());
        }
        return null;
    }

    private MemberDto authenticateUser(MemberDto memberDto) throws Exception {
        // 입력한 아이디를 가지고 회원정보를 조회
        List<MemberDto> list = memberRepository.findByUserId(memberDto.getUserId());
        // 입력한 아이디로 조회된 회원정보가 없으면 null 반환
        if (list.size() == 0) return null;
        // 입력된 회원목록 비밀번호 복호화
        for(MemberDto member : list){
            member.setPassword(Password.decrypt(member.getPassword()));
        }
        // 조회된 회원정보의 비밀번호와 입력한 비밀번호를 비교
        for(MemberDto member : list){
            if(member.getPassword().equals(memberDto.getPassword())) return member;
        }
        return null;
    }
}
