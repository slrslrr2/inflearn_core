package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class PrototypeBeanFind {

    @Test
    public void singletonBeanFine(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        System.out.println("1. getBean 하고");
        ProtoTypeBean protoTypeBean = ac.getBean(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);

        ac.close();

        /**
            ProtoTypeBean.init
            ProtoTypeBean.init

            find prototypeBean
            protoTypeBean = hello.core.scope.PrototypeBeanFind$ProtoTypeBean@78aea4b9

            find prototypeBean2
            protoTypeBean2 = hello.core.scope.PrototypeBeanFind$ProtoTypeBean@2a65bb85
        */

        /**
            초기화 메서드 실행 시점 : 빈 조회할 때 생성
            프로토타입 빈을 2번 조회하고, *다른 스코프 빈이 생성*됨, 초기화도 2번 실행
            스프링컨테이너가 생성과 의존관계주입만 관여함
            스프링컨테이너가 종료는 관리 안하기에 destroy 실행안함
        */
    }

    @Scope("prototype")
    static class ProtoTypeBean{
        @PostConstruct
        public void init(){
            System.out.println("2. ProtoTypeBean.init이 호출됨");
            // 요청(getBean)시마다 매번 다른 객체를 반환하니까
            // getBean -> 객체생성(init)
        }

        @PreDestroy
        public void destroy(){
            System.out.println("ProtoTypeBean.destroy");
        }
    }
}
