package jobis.jobisimpTask.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jobis.jobisimpTask.dto.MemberDto;
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

    @Tag(name = "회원가입 API , 등록된 사용자와 일치한 정보만 회원 가입 가능")
    @Operation(summary = "업체 회원가입", description = "업체 측에서 회원가입 할 때 사용하는 API")
    @ApiResponse(responseCode = "1000", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto) {
        memberService.saveMember(memberDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
