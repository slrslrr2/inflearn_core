package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {
    DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void join() {
        // given
        Member member = new Member(1L, "gbitkim", Grade.VIP);

        // when
        int discount = discountPolicy.discount(member, 20000);

        // then
        Assertions.assertThat(2000).isEqualTo(discount);
    }

    @Test
    @DisplayName("VIP가 아니면 할인에 적용되지 않아야 한다")
    void join2() {
        // given
        Member member = new Member(1L, "gbitkim2", Grade.BASIC);

        // when
        int discount = discountPolicy.discount(member, 20000);

        // then
        Assertions.assertThat(2000).isEqualTo(discount);
    }


}