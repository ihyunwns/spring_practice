package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS) // 이 빈은 HTTP 요청 당 하나씩 생성되고, 요청이 끝나는 시점에 소멸된다.
                            //가짜 프록시 객체를 생성해서 주입할 수 있도록 해준다. 그래서 확인해보면 MyLogger$$EnhancerBySpringCGLIB 이라는 클래스로 만들어져있는 것을 볼 수가 있다.

                            // Provider를 사용하든 프록시를 이용하든 중요한 점은 진짜 객체 조회를 꼭 필요한 시점까지 지연 처리 한다는 점이다.
public class MyLogger {
    private String uuid;

    @Setter
    private String requestURL;

    public void log(String message) {
        System.out.println("[" + uuid + "]" + "[" + requestURL + "]" + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close" + this);
    }

}
