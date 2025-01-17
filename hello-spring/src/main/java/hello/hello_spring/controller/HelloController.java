package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello") // GET 요청, /hello
    public String Hello(Model model) {
        model.addAttribute("data", "hello!!");
        return "hello"; //resources에 있는 hello에게 넘겨줌
    }

    @GetMapping("hello-mvc")
    // = @RequestParam(value="name", required = true) >> default
    // params 필요, ?name(value)=name(String name)
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name; //
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        // json 데이터의 key값은 setter 이름에 따라 바뀜
        // setTest(
        public void setName(String name) {
            this.name = name;
        }
    }

}


