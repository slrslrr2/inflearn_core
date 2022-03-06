package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredOption {
    // 옵션 테스트
    // 주입할 스프링 빈이 없어도 동작하고 싶은경우

    @Test
    void AutowiredOption(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean{
        // 참고로 Member는 @Component가 아니다
        // 즉, 스프링컨테이너에서 관리되는 Bean객체가 아니다.

        // 1. @Autowired(required = false)
        // 결과 : 의존관계가 없으면 해당 베서드 자체가 호출이 안된다.
        @Autowired(required = false)
        public void noBean(Member noBean){
            System.out.println("noBean = " + noBean);
        }

        // 2. @Nullable
        // 결과 : noBean2 = null
        // 호출은 되지만 null
        @Autowired
        public void noBean2(@Nullable Member noBean2){
            System.out.println("noBean2 = " + noBean2);
        }

        // 3. Optional //Java8부터 지원
        // 결과 : noBean3 = Optional.empty
        // 호출은 되지만 Optional의 번객체
        @Autowired
        public void noBean3(Optional<Member> noBean3){
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
