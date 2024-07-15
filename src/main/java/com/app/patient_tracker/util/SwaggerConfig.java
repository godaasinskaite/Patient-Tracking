package com.app.patient_tracker.util;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 * <a href="http://localhost:1453/swagger-ui/">http://localhost:1453/swagger-ui/</a>
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.app.patient_tracker"))
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Patient Tracking Application")
                .description("This is the application created to work with patient related operations: registering new patient, assessing, marking attendance, tracking progress.")
                .version("1.0.0")
                .build();
    }
}