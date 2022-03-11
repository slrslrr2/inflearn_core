package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonBeanFind {

    @Test
    public void singletonBeanFine(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean = ac.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = ac.getBean(SingletonBean.class);

        System.out.println("singletonBean = " + singletonBean);
        System.out.println("singletonBean2 = " + singletonBean2);

        assertThat(singletonBean).isSameAs(singletonBean2);
        ac.close();

        /**
         SingletonBean.init
         singletonBean = hello.core.scope.SingletonBeanFind$SingletonBean@78aea4b9
         singletonBean2 = hello.core.scope.SingletonBeanFind$SingletonBean@78aea4b9
             23:57:23.164 [Test worker] DEBUG org.springframework.context.annotation.AnnotationConfigApplicationContext - Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@7e3181aa, started on Wed Mar 09 23:57:22 KST 2022
             SingletonBean.destroy
         */

        /**
         @Scope("singleton")
             같은 인스턴스를 조회한다.
             SingletonBean는 init하고 destory한다.
             초기화 메서드 실행시점 : 스프링 컨테이너 생성 시점
             스프링컨테이너가 관리하기에 종료 빈 메서드 실행함(destroy)
         */
    }

    @Scope("singleton")
    static class SingletonBean{
        @PostConstruct
        public void init(){
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonBean.destroy");
        }
    }
}
