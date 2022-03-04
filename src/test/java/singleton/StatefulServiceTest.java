package singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    void statefulServiceSingleton(){
        StatefulService statefulService = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        statefulService.order("user1", 1000);
        statefulService2.order("user2", 2000);

        // user1이 조회를 하였지만 값이 1000이 아니라 2000이 나왔다
        // why? 싱글스레드는 같은 인스턴스를 재사용하기때문에
        // 공통 사용되는 클래스 변수로 인해, user2가 2000원을 적용하였기 때문이다.
        Assertions.assertThat(statefulService.getPrice()).isEqualTo(2000);
    }

    @Configuration
    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }
}