package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    void configutarionTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        MemberRepository memberRepository2 = memberService.getMemberRepository();
        MemberRepository memberRepository3 = orderService.getMemberRepository();

        System.out.println("memberRepository => " + memberRepository);
        System.out.println("memberRepository2 => " + memberRepository2);
        System.out.println("memberRepository3 => " + memberRepository3);

        Assertions.assertThat(memberRepository).isSameAs(memberRepository2);
        Assertions.assertThat(memberRepository).isSameAs(memberRepository3);
    }
}
