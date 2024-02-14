package jobis.jobisimpTask.service;

import jobis.jobisimpTask.crypto.Password;
import jobis.jobisimpTask.crypto.ResidentRegistration;
import jobis.jobisimpTask.entity.MemberEntity;
import jobis.jobisimpTask.jwt.JwtUtils;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.repository.UserdataRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private MemberRepository memberRepository;
    private UserdataRepository userdataRepository;
    private JwtUtils jwtUtils;
    private ModelMapper modelMapper;

    @Value("${jwt.secret.key}")
    private String secretKey;
    @Value("${jwt.expirationMs}")
    private Long expiredTimeMs;
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
    @Override
    public boolean saveMember(MemberEntity memberEntity) {
        // USERDATA 테이블 검증
        boolean existsInUserData = userdataRepository.existsByRegNo(memberEntity.getRegNo());
        // MEMBER 테이블 검증
        boolean existsInMember = memberRepository.existsByUserId(memberEntity.getUserId());
        try {
            if (existsInUserData && !existsInMember) {
                String encryptedPassword = Password.encrypt(memberEntity.getPassword());
                memberEntity.setPassword(encryptedPassword);
                String encryptRegistration = ResidentRegistration.encrypt(memberEntity.getRegNo());
                memberEntity.setRegNo(encryptRegistration);
                memberRepository.save(memberEntity); // 회원 정보 저장
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
    public String login(MemberEntity memberEntity)  {
        String userId = memberEntity.getUserId();
        String password = memberEntity.getPassword();
        MemberEntity member = findId(userId);
        if (member == null) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        if (!matchPassword(password, member.getPassword())) throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        MemberEntity info = modelMapper.map(member, MemberEntity.class);
        return jwtUtils.createAccessToken(info);
    }

    // ID 찾기
    private MemberEntity findId (String userId) {
        return memberRepository.findByUserId(userId);
    }

    // 비밀번호 비교
    private boolean matchPassword(String inputPassword, String dbPassword){
        try {
            // 입력된 회원목록 비밀번호 복호화
            dbPassword = Password.decrypt(dbPassword);
            return dbPassword.equals(inputPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
