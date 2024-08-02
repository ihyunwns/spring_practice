package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // 요청이 오면 관련 컨트롤로거 있는지 찾고 없으면 static을 찾기 때문에 home.html이 불러와지게 된다.
    public String home() {
        return "home";
    }

}
