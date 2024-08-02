package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1");
        // 이때 프로토타입 빈 생성
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        // 한번 더 생성, 싱글톤 빈은 같은 빈을 공유하지만 프로토타입은 또 생성을 하게 된다.
        // 그리고 이떄 스프링에서는 생성하고 더 이상 관리를 하지 않기 때문에 종료 메서드가 호출 되지 않는다.

        // 싱글톤 빈과 같이 사용하게 된다면 어떻게 될 까? --> 문제 발생
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
