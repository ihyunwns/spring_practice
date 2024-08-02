package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class WebScopeTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void webScopeTest1() {
        System.out.println("test" + applicationContext);
    }


}
