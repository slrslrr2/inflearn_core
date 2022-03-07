package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
//@Primary // 해당 함수를 사용하여 같은 DiscountPolicy 를 상속받은 클래스가 @Component를 2개이상 가졌다면,
         // NoUniqueBeanDefinitionException 오류가 나기에
         // @Primary를 붙임으로써 스프링컨테이너의 우선순위를 결정한다.
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {
    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){
            return price * discountPercent / 100;
        } else {
            return price;
        }
    }
}
