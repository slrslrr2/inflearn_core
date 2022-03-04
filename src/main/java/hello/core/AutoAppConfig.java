package hello.core;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", // basePackages를 붙이지 않으면  
                                    // @ComponentScan을 붙인 해당 클래스의 패키지가 기본값이된다.
                                    // SpringBoot를 사용하면 @SpringBootApplication이 생성되는데
                                    // @SpringBootApplication안에 @ComponentScan이 등록되어있다.

                                    // @ComponentScan 자동대상은 아래와 같다.
                                    // @Component, @Controller, @Service, @Repository, @Configuration
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
