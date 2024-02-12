package jobis.jobisimpTask;

import jobis.jobisimpTask.controller.MemberController;
import jobis.jobisimpTask.dto.MemberDto;
import jobis.jobisimpTask.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberControllerTest {

    @Mock
    private MemberService memberService;

    @InjectMocks
    private MemberController memberController;

    @Test
    @DisplayName("회원가입 테스트 : 회원가입 성공시 200 반환")
    public void testRegisterUserSuccess() {
        // Given
        MemberDto memberDto = new MemberDto();
        when(memberService.saveMember(memberDto)).thenReturn(true);

        // When
        ResponseEntity<?> responseEntity = memberController.registerUser(memberDto);

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("회원 가입 성공", responseEntity.getBody());
    }

    @Test
    @DisplayName("회원가입 실패 테스트 : 회원가입 실패시 400 반환")
    public void testRegisterUserFailure() {
        // Given
        MemberDto memberDto = new MemberDto();
        when(memberService.saveMember(memberDto)).thenReturn(false);

        // When
        ResponseEntity<?> responseEntity = memberController.registerUser(memberDto);

        // Then
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals("회원 가입 실패", responseEntity.getBody());
    }
}