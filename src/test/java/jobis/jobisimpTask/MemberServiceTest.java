package jobis.jobisimpTask;

import jobis.jobisimpTask.entity.MemberEntity;
import jobis.jobisimpTask.jwt.JwtUtils;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.service.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceTest {

    private MemberRepository memberRepository;
    private JwtUtils jwtUtils;
    private MemberServiceImpl memberService;

    @BeforeEach
    void setUp() {
        memberRepository = mock(MemberRepository.class);
        jwtUtils = mock(JwtUtils.class);
//        memberService = new MemberServiceImpl(memberRepository, jwtUtils);
    }

    @Test
    @DisplayName("회원가입 실패시 토큰 반환 없음")
    void login_FailedAuthentication_ReturnsNull() throws Exception {
        // Given
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setUserId("testUser");
        memberEntity.setPassword("incorrectPassword");

        when(memberRepository.findByUserId(memberEntity.getUserId())).thenReturn(List.of());

        // When
        String token = memberService.login(memberEntity);

        // Then
        assertEquals(null, token);
    }
}
