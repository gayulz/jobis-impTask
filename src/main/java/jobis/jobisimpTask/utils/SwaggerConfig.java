package jobis.jobisimpTask.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "삼쩜삼 과제 App",
                description = "api명세",
                version = "v1"))
@RequiredArgsConstructor


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/szs/**"};

        return GroupedOpenApi.builder()
                .group("회원가입 API v1")
                .pathsToMatch(paths)
                .build();
    }


}

