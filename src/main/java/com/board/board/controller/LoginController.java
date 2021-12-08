package com.board.board.controller;

import com.board.board.Dto.LoginFormDto;
import com.board.board.Dto.MemberFormDto;
import com.board.board.constant.Level;
import com.board.board.constant.Role;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Member;
import com.board.board.repository.MemberRepository;
import com.board.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;



    @RequestMapping("/add")
    public String addForm(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto){
        return "members/addMemberForm";
    }



    @PostMapping("/add")
    public String save(@Valid @ModelAttribute MemberFormDto memberFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";

        }
        if(memberFormDto.getCh_password().equals(memberFormDto.getPassword())){
            Member member=new Member();
            member.setPassword(memberFormDto.getPassword());
            member.setRole(Role.USER);
            member.setName(memberFormDto.getName());
            member.setEmail(memberFormDto.getEmail());
            member.setLevel(Level.level1);
            memberRepository.save(member);
            return "redirect:/";
        }
        else{
            bindingResult.reject("addMemberFail","비밀번호가 달라요~");
            return "members/addMemberForm";
        }

    }


    @RequestMapping("/login")
    public String loginForm(@ModelAttribute("loginFormDto")LoginFormDto loginFormDto){
            return "members/loginForm";
    }



     @PostMapping("/login")
    public String login1(@Valid @ModelAttribute("loginFormDto")LoginFormDto loginFormDto, BindingResult bindingResult,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "members/loginForm";
        }
        Member loginMember=loginService.Login(loginFormDto.getEmail(),loginFormDto.getPassword());
        if (loginMember==null){
            bindingResult.reject("loginFail","이메일 또는 비밀번호가 맞지 않습니다.");
            return "members/loginForm";
        }
        //로그인 성공처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성(기본값이 true)
        HttpSession session=request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER,loginMember);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(false);//있는 세션 가져오고 만약 없으면 null반환
        log.info("로그아웃1");
        if(session != null){
            session.invalidate();

        }
        log.info("로그아웃2");
        return "redirect:/";
    }
}
