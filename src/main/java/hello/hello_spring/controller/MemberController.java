package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

// 이 어노테이션은 스프링 컨테이너에 컨트롤러 객체(스프링 Bean)로 등록하는 어노테이션
@Controller
public class MemberController {

    private final MemberService memberService;

    // 여러 컨트롤러에서 동일한 서비스에 접근할텐데, 그때마다 서비스를 생성하는 건 불필요한 낭비
    // 따라서 스프링은 Autowired 어노테이션을 통해 스프링 컨테이너에 등록된 Bean에 의존성을 주입해준다.
    // 즉, 매번 생성하는게 아니라 처음에 @Conttoller처럼 스프링 컨테이너에 등록된 Bean에다가
    // 다른 계층, 즉 다른 Bean을 연결해주는 것이다. 그럼 처음 스프링 부트가 시작될 때 각 스프링 빈은 처음 한번만 생성되고
    // 서로 연결관계를 가지게 되어 불필요한 객체 생성을 방지할 수 있다.
    // 이렇게 각 계층별 빈이 하나만 생성되기 때문에 싱글턴 패턴이라고 한다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}




