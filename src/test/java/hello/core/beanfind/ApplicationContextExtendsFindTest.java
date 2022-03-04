package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsFindTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ConfigTest.class);

    @Test
    @DisplayName("부모타입으로 조회 시,자식이 둘 이상 있으면 중복오류가 발생한다.")
    void findBeanType() {
        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("둘 이상의 자식이 있는 경우, 빈 이름을 지정하면 된다.")
    void findBeanParentTypeBeanName() {
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(rateDiscountPolicy).isInstanceOf(DiscountPolicy.class);
    }

    @Test
    @DisplayName("둘 이상의 자식이 있는 경우, 빈 클래스를 직접 지정하면 된다.")
    void findBeanParentSubType() {
        DiscountPolicy fixDiscountPolicy = ac.getBean(FixDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(fixDiscountPolicy).isInstanceOf(FixDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모를 상속받은 모든 자식들을 조회한다.")
    void findAllBeanParentType(){
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value=>" + beansOfType.get(key));
        }
    }

    @Test
    @DisplayName("부모를 상속받은 모든 자식들을 조회한다.")
    void findAllBeanObject(){
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + " value=>" + beansOfType.get(key));
        }
    }

    @Configuration
    static class ConfigTest{
        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }

        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }
    }
}
