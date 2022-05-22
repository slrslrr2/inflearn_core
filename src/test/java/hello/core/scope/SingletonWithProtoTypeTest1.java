package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithProtoTypeTest1 {
    
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype(){
        /**
            ClientBean 안에 PrototypeBean을 만든다.
            PrototypeBean은 getBean할때마다 새로 생성되는 스코프를 가졌다.
        */
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isSameAs(1);

        /**
         ClientBean은 singleton인데 PrototypeBean을 안에 미리 등록해놓았는데
            PrototypeBean을 불러올 경우
                getBean으로 새로운 객체를 불러오는것이 아니므로
                    PrototypeBean도 싱글톤처럼 사용된다.
                        ==> ac.getBean(ClientBean.class)하여도
                            같은 싱글톤ClientBean을 가져오는것이기에
                            PrototypeBean 계속 count가 1씩 늘어난다.
         */
        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isSameAs(2);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean{
        private final PrototypeBean prototypeBean;

        public int logic(){
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean2{
//        private final PrototypeBean prototypeBean;
        /**
           PrototypeBean을 ClientBean2 안에서도
           항상 새로운 객체로 만드는 방법 ==> ObjectProvider<PrototypeBean> getObject
        **/
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanObjectProvider;

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanObjectProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean{
        private int count = 0;
        
        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("SingletonWithProtoTypeTest1.init" + this);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("SingletonWithProtoTypeTest1.destroy");
        }
    }
}
