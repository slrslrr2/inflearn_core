package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
        
        // @Bean으로 들어간 것들을
        // 스프링 컨테이너에 관리해준다.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        MemberService memberService2 = applicationContext.getBean("memberService", MemberService.class);


        // Command + Option + V
        // new Member(1L, "memberA", Grade.VIP); 만 치고 Member커서 위에
        // 위 단축키 클릭 시
        // 앞에 변수 타입과 변수명이 자동으로 써짐
        Member memberA = new Member(1L, "memberA", Grade.VIP);
        memberService.join(memberA);

        Member findmember = memberService.findMember(1L);

        // sottv
        System.out.println("new memberA = " + memberA);
        System.out.println("findmember = " + findmember);
    }
}
