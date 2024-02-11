package jobis.jobisimpTask.utils;

import jobis.jobisimpTask.service.MemberService;
import jobis.jobisimpTask.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // MemberService 생성
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl();
    }

}
