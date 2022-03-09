package hello.core.autowired;

import hello.core.AutoAppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        DiscountService discountService = ac.getBean(DiscountService.class);

        Member member = new Member(1L, "gbitkim", Grade.VIP);
        int discountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");
        Assertions.assertThat(1000).isEqualTo(discountPrice);

        int discountPrice2 = discountService.discount(member, 10000, "rateDiscountPolicy");
        Assertions.assertThat(2000).isEqualTo(discountPrice2);
    }

    static class DiscountService{
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        // @Autowired 생성자가 하나기때문에 생략
        // policyMap객체에 key 값을 자동으로 bean이름으로 등록해주고, 객체도 자동주입한다.
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;

            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
        }

        public int discount(Member member, int price, String DiscountPolicy){
            DiscountPolicy discountPolicy = policyMap.get(DiscountPolicy);

            return discountPolicy.discount(member, price);
        }
    }
}
