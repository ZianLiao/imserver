package com.css.im.config;

import com.css.common.GlobalAPIString;
import com.css.common.ResultCode;
import com.css.swagger.ModelCache;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by wx on 2020-07-06
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {

        // 添加请求参数，我们这里把token作为请求头部参数传入后端
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<Parameter>();
        parameterBuilder.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        parameters.add(parameterBuilder.build());

        ModelCache.getInstance().setParamClass(GlobalAPIString.class);

        //添加全局响应状态码
        List<ResponseMessage> responseMessageList = new ArrayList<ResponseMessage>();
        Arrays.asList(ResultCode.REQ_SUCCESS, ResultCode.FORBIDDEN, ResultCode.UNAUTHORIZED,
                ResultCode.PERMISSION_TOKEN_EXPIRED, ResultCode.PERMISSION_TOKEN_INVALID,
                ResultCode.PERMISSION_SIGNATURE_ERROR).stream().forEach(errorEnum -> {
            responseMessageList.add(
                    new ResponseMessageBuilder().code((int) errorEnum.getCode()).message(errorEnum.getMessage()).responseModel(
                            new ModelRef(errorEnum.getMessage())).build()
            );
        });

        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.css.im"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("即时通信服务接口")
                .description("即时通信服务端 RESTful APIs")
                .termsOfServiceUrl("http://localhost:8080/")
                .contact("wuxu@css.com.cn")
                .version("1.0")
                .build();
    }


}
