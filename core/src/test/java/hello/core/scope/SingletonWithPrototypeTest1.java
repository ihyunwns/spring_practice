package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
        //의도된 바와는 다르다.
        //프로토타입 빈은 싱글톤 처럼 같은 빈을 공유하는 것이 아니라 요청마다 새로운 빈을 생성해야 하는데 count값이 증가하는 것을 볼 수가 있다.
    }


    @Configuration
    @Scope("singleton")
    static class ClientBean {

        //private final PrototypeBean prototypeBean; //생성 시점에 주입되서 싱글톤인 ClientBean은 계속 이 프로토타입 빈을 사용한다.

//        @Autowired // 싱글톤 빈은 생성 시점에서만 의존 관계를 주입 받기 때문에 '주입 시점에' 프로토타입 빈이 새로 생성되기는 하지만 싱글톤 빈과 함께 계속 유지된다.
//        public ClientBean(PrototypeBean prototypeBean) { //PrototypeBean도 설정정보로 등록을 해줘야 컴파일 에러 안뜸
//            System.out.println("ClientBean construct");
//            this.prototypeBean = prototypeBean;
//        }

        // 스프링부트의 기능
//        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        // 자바 표준 기능, 라이브러리 불러와야 함
        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }


    @Component
    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init / " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
