package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class PrototypeBeanFind {

    @Test
    public void singletonBeanFine(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean = ac.getBean(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);

        System.out.println("find prototypeBean");
        System.out.println("protoTypeBean = " + protoTypeBean);
        System.out.println("find prototypeBean2");
        System.out.println("protoTypeBean2 = " + protoTypeBean2);

        assertThat(protoTypeBean).isNotSameAs(protoTypeBean2);
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
            System.out.println("ProtoTypeBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("ProtoTypeBean.destroy");
        }
    }
}
