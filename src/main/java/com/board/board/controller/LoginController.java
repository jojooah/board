package com.board.board.controller;

import com.board.board.Dto.MemberFormDto;
import com.board.board.constant.Level;
import com.board.board.constant.Role;
import com.board.board.entity.Member;
import com.board.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    MemberRepository memberRepository;

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
           return "members/addMemberForm";
        }

    }
}
