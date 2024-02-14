package jobis.jobisimpTask.service;

import jobis.jobisimpTask.crypto.Password;
import jobis.jobisimpTask.crypto.ResidentRegistration;
import jobis.jobisimpTask.entity.MemberEntity;
import jobis.jobisimpTask.jwt.JwtUtils;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.repository.UserdataRepository;
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


//    @Autowired
//    public MemberServiceImpl(MemberRepository memberRepository , JwtUtils jwtUtils) {
//        this.memberRepository = memberRepository;
//        this.jwtUtils = jwtUtils;
//    }


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
    public String login(MemberEntity memberEntity) {
        MemberEntity authenticatedMember;
        try {
            authenticatedMember = authenticateUser(memberEntity);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (authenticatedMember != null) {
            return jwtUtils.generateJwtToken(authenticatedMember.getName());
        }
        return null;
    }

    private MemberEntity authenticateUser(MemberEntity memberEntity) throws Exception {
        // 입력한 아이디를 가지고 회원정보를 조회
        List<MemberEntity> list = memberRepository.findByUserId(memberEntity.getUserId());
        // 입력한 아이디로 조회된 회원정보가 없으면 null 반환
        if (list.size() == 0) return null;
        // 입력된 회원목록 비밀번호 복호화
        for(MemberEntity member : list){
            member.setPassword(Password.decrypt(member.getPassword()));
        }
        return comparison(list, memberEntity);
    }

    // 조회된 회원정보 리스트와 함께 비밀번호와 입력한 비밀번호를 비교
    public MemberEntity comparison(List<MemberEntity> list, MemberEntity memberEntity){
        for(MemberEntity member : list){
            if(member.getPassword().equals(memberEntity.getPassword())) return member;
        }
        return null;
    }
}
