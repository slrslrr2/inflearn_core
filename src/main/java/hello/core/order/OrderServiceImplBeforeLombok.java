package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImplBeforeLombok implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    /**
     * 참고로 @Autowired는 @Component이기에 동작하고, 일반자바 객체는 동작하지않는다.
     * 자동의존주입은 스프링컨테이너가 관리하는 스피링빈이여야한다.
     *
     * 1. 의존주입 - 생성자를 통해 의존관계 주입
     * 딱 1번만 호출됨 한번 정해진것은 수정되지 않음
     * 스프링 컨테이너에 빈을 통해 주입되고 나면 값이 수정 안됨
     * 불변, 필수인 의존관계에 사용한다.
     * 그렇기에 private final로 상단에 선언하여 필수로 선언함.
     *
     * @Autowired를 생략해도 된다.
     * OrderServiceImpl 생성자 함수가 하나만 있다면!
     */
    @Autowired
    public OrderServiceImplBeforeLombok(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;

        System.out.println("memberRepository = " + memberRepository);
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    /**
     *  2. 의존주입 - 수정자 주입
     *  선택, 변경이 가능하다
     *  첫번째 상단 선언에 final을 지운다.
     *  */
     private MemberRepository memberRepository2;

    /**두번째 Autowired를 선언하고 setter함수를 만든다.**/
     @Autowired
     public void setMemberRepository2(MemberRepository memberRepository2){
         System.out.println("memberRepository2 = " + memberRepository2);
        this.memberRepository2 = memberRepository2;
     }

    /**
     * 3. 의존주입 - 필드주입
     * 아래처럼 간결하게 사용할 수 있지만, Test코드에서는 좋지않다.
     * Why?
     * Test코드에서 memberRepository와 discountPolicy 빈을 사용하는 OrderServiceImpl.java를 테스트하고 싶은경우,
     * setMemberRepository, setDiscountPolicy를 만들고 사용해야하는 번거러움이 존재한다.
     * 혹은 전체 스프링 컨테이너를 띄우고 시작해야한다.
     *
     @Autowired private final MemberRepository memberRepository;
     @Autowired private final DiscountPolicy discountPolicy;

     그럼 어디서 사용할까?
     Test코드에서 작성해서 사용해도된다. 왜냐면 아무도 가져다 사용 안하니까
     */
}
