package com.you.springsecuritydemo.config;

import com.google.common.collect.Lists;
import com.you.springsecuritydemo.domain.constants.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName: SwaggerConfig
 * @Description: Swagger 的配置类
 *          swagger 的默认访问路径 http://ip:port/swagger-ui.html
 * @author: D
 * @create: 2020-06-23 15:36
 **/

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("swagger 接口文档")
                .apiInfo(apiInfo())

                .select()
                .apis(RequestHandlerSelectors.basePackage("com.you.springsecuritydemo.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(Lists.newArrayList(parameterBuilder().build()));
    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("D 的个人项目接口文档")
                .description("接口测试")
                .contact(new Contact("D","","d@qq.com"))
                .version("1.0")
                .build();
    }

    private ParameterBuilder parameterBuilder(){
        return new ParameterBuilder()
                .parameterType("header")
                .name("token")
                .description("token")
                .required(false)
                .modelRef(new ModelRef("String"));
    }


}
