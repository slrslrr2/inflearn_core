package hello.core.scan.filter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import static org.junit.jupiter.api.Assertions.*;

public class ComponentFilterApplConfigTest {

    @Test
    void getComponentMethod(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ComponentFilterAppconfig.class);
        BeanA beanA = ac.getBean("beanA", BeanA.class);
//        Assertions.assertThat(beanA).isInstanceOf(BeanA.class);


        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("beanB", BeanB.class));

    }

    @Configuration
    @ComponentScan(
            excludeFilters = { @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyExcludeComponent.class)},
            includeFilters = { @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = MyIncludeComponent.class)}
    )
    static class ComponentFilterAppconfig{

    }
}
