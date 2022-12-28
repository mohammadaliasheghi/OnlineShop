package com.google.OnlineShop.config;

import com.fasterxml.classmate.TypeResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.AlternateTypeBuilder;
import springfox.documentation.builders.AlternateTypePropertyBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@Profile("swagger")
@Slf4j
@Order
@RequiredArgsConstructor
@EnableSwagger2
public class SwaggerConfig {

    final private TypeResolver typeResolver;

    private Type pageableMixin() {
        return new AlternateTypeBuilder()
                .fullyQualifiedClassName(
                        String.format("%s.generated.%s",
                                Pageable.class.getPackage().getName(),
                                Pageable.class.getSimpleName()))
                .withProperties(Arrays.asList(
                        property(Integer.class,
                                "page"),
                        property(Integer.class,
                                "size"),
                        property(String.class,
                                "sort")
                ))
                .build();
    }

    private AlternateTypePropertyBuilder property(Class<?> type, String name) {
        return new AlternateTypePropertyBuilder()
                .withName(name)
                .withType(type)
                .withCanRead(true)
                .withCanWrite(true);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .alternateTypeRules(
                        new AlternateTypeRule(
                                typeResolver.resolve(Pageable.class),
                                typeResolver.resolve(pageableMixin()),
                                Ordered.HIGHEST_PRECEDENCE
                        ))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securitySchema()))
                .securityContexts(Collections.singletonList(securityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "API documentation of Loan App",
                "Use this documentation as a reference how to interact with app's API.",
                "1.0",
                "Terms of service",
                new Contact("mohammad", "https://github.com/mohammadaliasheghi?tab=repositories", "mohammad.ali.asheghi@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

    private OAuth securitySchema() {

        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope("shopping", "shopping Scope all"));

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant(ShoppingConstant.TOKEN_URL);

        grantTypes.add(creGrant);

        return new OAuth("oauth2schema", authorizationScopeList, grantTypes);

    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/**"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {

        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("shopping", "shopping Scope all");

        return Collections.singletonList(new SecurityReference("oauth2schema", authorizationScopes));
    }
}
