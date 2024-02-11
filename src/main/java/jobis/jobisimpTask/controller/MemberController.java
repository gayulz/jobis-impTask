package jobis.jobisimpTask.controller;


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

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto) {
        memberService.saveMember(memberDto);
        return ResponseEntity.ok("User registered successfully");
    }
}
