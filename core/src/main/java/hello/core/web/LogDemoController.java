package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    private final MyLogger myLogger;

    //private final ObjectProvider<MyLogger> myLoggerProvider;
    //Provider로 스프링이 실행될 때 MyLogger를 요청하는게 아니라 request 요청이 들어올 때만 호출된다.
    //Provider를 쓰지 않으면 애플리케이션을 실행할 때 LogDemoController와 LogDemoService가 Autowired로 MyLogger에 대해서 의존성을 주입받아야 하는데
    //MyLogger의 스코프는 request로 웹에서 요청이 와야 그떄서야 생성이 되기 때문에 실행 시점에서는 존재하지 않는 빈이기 때문에 오류가 나온다.
    //따라서 필요할 때 Dependency Lookup(DL) 할수 있는 Provider 기능을 이용해서 웹 요청이 들어올 때마다 생성을 할 수 있게 된다.

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        //MyLogger myLogger = myLoggerProvider.getObject();
        System.out.println("myLogger = " + myLogger.getClass());

        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }

}
