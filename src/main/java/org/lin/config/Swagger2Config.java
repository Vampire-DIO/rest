package org.lin.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 接口配置
 */

@Configuration
@EnableSwagger2
//@Profile({"dev","test"})
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true")
public class Swagger2Config {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("标题：业务_接口文档")
                        .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                        .contact(new Contact("lv", null, null))
                        .version("版本号:1.0")
                        .build())
                .select()
                //.apis(RequestHandlerSelectors.basePackage("com.hehe.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}