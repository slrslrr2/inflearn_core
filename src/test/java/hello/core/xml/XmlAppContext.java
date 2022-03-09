package hello.core.xml;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class XmlAppContext {

    @Test
    @DisplayName("Xml App Test")
    void getXmlAppContext(){
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("beanDefinitionName = " + beanDefinitionName);
        }
//                MemberService memberService = ac.getBean("memberSerivce", MemberService.class);
//        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
