package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

// 컨트롤러는 클라이언트의 http요청을 받아 처리하는 역할(모델과 view를 연결)
// 컨트롤러 내부에는 각각의 요청을 처리하는 메서드가 있음
// 만약 컨트롤러 내부에 해당 요청을 처리하는 메서드가 없으면 static 폴더에서 html을 찾음
// 컨트롤러는 기본적으로 view를 반환하고 @ResponseBody 어노테이션이 있으면 데이터를 반환
@Controller
public class HelloController {
    // @GetMapping은 get요청을 컨트롤러 메서드에 매핑한다는 소리
    // 이때 인자를 받는데, 이는 클라이언트가 요청을 보낼 경로를 의미
    // 컨트롤러 메서드가 문자열을 반환하면 View 이름(문자열.html)으로 해석하고 이를 viewResolver에게 전달
    // view 이름을 전달하면서 모델도 같이 넘김
    // 그럼 viewReolver(타임리프)가 해당 이름으로 된 view를 찾고 모델의 데이터를 반영하여 렌더링하고 이를 클라이언트에 전달
    // 컨트롤러의 메서드가 어노테이션없이 문자열이 아닌 다른값을 반환하면 오류발생(view 이름으로 해석을 못하니깐)
    // @RequestParam 어노테이션은 요청으로 들어온 데이터를 해당 매개변수로 매핑(여기선 name)
    // 만약 요청 파라미터의 키와 메서드의 파라미터의 이름이 같으면 생략 가능(즉 여기서 RequestParam의 인자 생략 가능)
    @GetMapping("hello")
    // 여기서 모델이란? 컨트롤러에서 view로 데이터를 전달하는 객체
    // 모델은 속성 이름과 속성 값의 형태로 데이터를 저장한다
    public String hello(Model model){
        model.addAttribute("name", "hello!");
        System.out.println("sdfsdfsdfsdf");
        return "hello";
    }
    // 옵션으로 required 설정하여 필수로 지정 가능, 기본값은 필수
    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "hello-template";
    }

    // ResponseBody 어노테이션을 쓰면 템플릿을 찾는게 아니라 데이터 그대로 HttpMessageConverter에 전달
    // 그럼 이 컨버터가 클라이언트에게 응답의 본문으로 데이터를 보내줌
    // 이때 return 값이 기본형 데이터이면 그대로 내려줌(int, boolean)
    // String은 래퍼클래스이긴 하지만 StringConverter가 처리
    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name){
        return 8 + name;
    }
    // 응답의 본문으로 내려줄 때 return 값이 객체이면 HttpMessageConverter에서
    // JsonConverter를 사용해서 json 형태로 내려줌
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


}
