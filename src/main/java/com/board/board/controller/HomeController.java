package com.board.board.controller;

import com.board.board.constant.SessionConst;
import com.board.board.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
@RequestMapping
@Slf4j
public class HomeController {
    @GetMapping("/home")
    public String home(@SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember, Model model){
        if(loginMember==null){
            log.info("로그아웃상태");
            return "index1";
        }
        log.info("로그인상태");
        model.addAttribute("member",loginMember);
        return "index";
    }
}
