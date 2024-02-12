package jobis.jobisimpTask;

import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.repository.MemberRepository;
import jobis.jobisimpTask.service.MemberServiceImpl;
import jobis.jobisimpTask.utils.AppConfig;
import jobis.jobisimpTask.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
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
        AppConfig appConfig = new AppConfig();
        memberService = (MemberServiceImpl) appConfig.memberService();
        jwtUtils = mock(JwtUtils.class);
        memberRepository = mock(MemberRepository.class);
    }

    @Test
    void login_SuccessfulAuthentication_ReturnsJwtToken() throws Exception {
        // Given
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId("test");
        memberDto.setPassword("1231231243");
        memberDto.setName("홍길동");
        memberDto.setRegNo("860824-1655068");


        MemberDto authenticatedMember = new MemberDto();
        authenticatedMember.setUserId("test");
        authenticatedMember.setName("1231231243");

        when(memberRepository.findByUserId(memberDto.getUserId())).thenReturn(List.of(authenticatedMember));
        when(jwtUtils.generateJwtToken(authenticatedMember.getName())).thenReturn("mockedJwtToken");

        // When
        String token = memberService.login(memberDto);

        // Then
        assertEquals("mockedJwtToken", token);
    }

    @Test
    void login_FailedAuthentication_ReturnsNull() throws Exception {
        // Given
        MemberDto memberDto = new MemberDto();
        memberDto.setUserId("testUser");
        memberDto.setPassword("incorrectPassword");

        when(memberRepository.findByUserId(memberDto.getUserId())).thenReturn(List.of());

        // When
        String token = memberService.login(memberDto);

        // Then
        assertEquals(null, token);
    }
}
