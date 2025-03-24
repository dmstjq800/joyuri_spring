package project.demo.home.contoller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import project.demo.member.service.MemberService;


@RestController
@RequiredArgsConstructor
public class HomeContoller {
    private final MemberService memberService;

    @GetMapping("/home")
    public String HomeContoller() {

        return memberService.getCurrentUsername();
    }
}
