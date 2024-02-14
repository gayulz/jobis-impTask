package jobis.jobisimpTask.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jobis.jobisimpTask.entity.MemberEntity;
import jobis.jobisimpTask.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/szs")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Tag(name = "회원가입 API")
    @Operation(summary = "회원가입", description = "홍길동/김둘리/마징가/베지터/손오공 외 가입불가")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberEntity memberEntity) {
        boolean result = memberService.saveMember(memberEntity);
        if (result) {
            return ResponseEntity.ok().body("회원 가입 성공");
        } else {
            return ResponseEntity.badRequest().body("회원 가입 실패");
        }
    }
    
    @Tag(name = "로그인 API")
    @Operation(summary = "로그인", description = "로그인 성공시 JWT 토큰 발급")
    @ApiResponse(content = @Content(mediaType = "application/json"))
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberEntity memberEntity) {
        String accessToken = memberService.login(memberEntity);
        if (accessToken != null) {
            return ResponseEntity.ok().body(accessToken);
        } else {
            return ResponseEntity.badRequest().body("로그인 실패");
        }
    }
}
