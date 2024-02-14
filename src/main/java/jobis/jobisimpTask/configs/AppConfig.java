package jobis.jobisimpTask.configs;

import jobis.jobisimpTask.service.MemberService;
import jobis.jobisimpTask.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    // MemberService 주입 (MemberServiceImpl)
    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl();
    }

}
