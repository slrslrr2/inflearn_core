package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {
    @Test
    public void lifeCycleTest(){
        /**
         * 상속관계는 아래와 같다.
         * 부모는 자식을 담을 수 있다.
         * AnnotationConfigApplicationContext -> ConfigurableApplicationContext -> ApplicationContext
         */
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        ac.getBean(NetworkClient.class);
        /**
         * 1. 생성자 호출 url null
         * 2. connect :null
         * 3. call: null message = 초기화 연결 메시지
         *
         * 위 소스를 실행하면 다음과 같다.
         * 왜 null이 떨어질까?
         *
             *     public NetworkClient() {
             *         System.out.println("생성자 호출 url " + url);
             *         connect();
             *         call("초기화 연결 메시지");
             *     }
         *
         * 1,2,3 라인은 모두 객체를 생성할 때 호출된 함수이고 (생성자 함수에 url을 Set하기 전에 모두 호출됨)
         * 객체를 생성하고 난 후 setUrl을 주었기 때문이다.
         *
         * 스프링빈은 다음과 같은 라이프사이클을 가진다.
         * [객체생성 -> 의존관계 주입]
         *
         * 여기서 잠깐, [초기화 작업]이란? 객체 생성 -> 의존관계 주입 후 필요한 값들이 다 연결된 상태!(지금의 setUrl 들어간 상태)
         * 그렇다면 스프링은 의존관계 주입이 완료되고 초기화해야하는 시점을 어떻게 알려줄까?
         * 또, 스프링 빈의 이벤트 라이프사이클은 어떻게 될까?
         * 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
         */

        /*
        1. 스프링 빈의 초기화 시점, 소멸 직전 시점 알려주기 - 1. 인터페이스(InitializingBean, DisposableBean)
        ㄴ 예전에 나왔고 잘 사용하지는 않음.

        @Autowired의존관계 주입 후 실행 함수 : InitializingBean - afterPropertiesSet함수를 상속받아 사용한다.
        ac.close() 직전 실행 함수 : DisposableBean - destroy 함수를
         */
        ac.close();

    }

    @Configuration
    static class LifeCycleConfig{

        @Bean
        public NetworkClient networkClient(){
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }
}
