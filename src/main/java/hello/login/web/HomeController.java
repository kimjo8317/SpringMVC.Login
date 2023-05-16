package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping
    String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if(memberId == null) {
            return "home";
        }
        //로그인 (db에서 찾은 member가 없으면 홈으로)
        Member loginMember = memberRepository.findId(memberId);
        if (loginMember == null) {
            return "home";
        }
        //로그인성공 로직
        model.addAttribute("member", loginMember);
        return "loginHome";

    }
}