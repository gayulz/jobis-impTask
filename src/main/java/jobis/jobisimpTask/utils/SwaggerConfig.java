package jobis.jobisimpTask.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "3o3 과제 API",
                description = "api명세",
                version = "v1.0.0"))
@RequiredArgsConstructor


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/szs/**"};

        return GroupedOpenApi.builder()
                .group("백엔드 엔지니어 채용 과제 API")
                .pathsToMatch(paths)
                .build();
    }
}

