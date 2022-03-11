package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    /**
    1. 어노테이션은 인터페이스이다.
    (인터페이스) MainDiscountPolicy 이름을 가진 어노테이션을 만들면 위와 같다.
        public @interface MainDiscountPolicy 만 쓰면 어노테이션 완성!
        다른 메소드에 @MainDiscountPolicy을 붙여 사용 가능

    2. MainDiscountPolicy의 역할
        @Qualifier을 통해 @Component가 스프링컨텍스트에 올라갈 경우
        bean이름의 구분자를 추가해주는 것이다.

    3. 만든 어노테이션 사용방법
        @Qualifier을 사용할 컴포넌트에 해당 어노테이션을 붙인다.

        예시
        @MainDiscountPolicy
        public class RateDiscountPolicy implements DiscountPolicy {

        @Autowired될 생성자/수정자 등 함수에 해당 어노테이션을 붙인다.
        예시
        public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
            this.memberRepository = memberRepository;
            this.discountPolicy = discountPolicy;
        }
     **/
}
