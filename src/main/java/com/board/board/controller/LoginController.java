package com.board.board.controller;

import com.board.board.Dto.ChangePwdDto;
import com.board.board.Dto.FindPwdDto;
import com.board.board.Dto.LoginFormDto;
import com.board.board.Dto.MemberFormDto;
import com.board.board.constant.Level;
import com.board.board.constant.Role;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Board;
import com.board.board.entity.BoardScrap;
import com.board.board.entity.Comment;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.BoardScrapRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.MemberRepository;
import com.board.board.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    LoginService loginService;

    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;


    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final BoardScrapRepository boardScrapRepository;


    @RequestMapping("/add")
    public String addForm(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto){
        return "members/addMemberForm";
    }



    @PostMapping("/add")
    public String save(@Valid @ModelAttribute MemberFormDto memberFormDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "members/addMemberForm";

        }
        String mail=memberFormDto.getEmail();
        String nickname=memberFormDto.getName();

        Member cur_member=memberRepository.findByEmail(mail).orElse(null);

        if(cur_member!=null){
            bindingResult.reject("addMemberFail","?????? ????????? ???????????????");
            return "members/addMemberForm";
        }
        Member cur_member2=memberRepository.findByName(nickname).orElse(null);
        if(cur_member2!=null){
            bindingResult.reject("addMemberFail","???????????? ?????? ????????? ?????????");
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
            bindingResult.reject("addMemberFail","??????????????? ?????????~");
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
            bindingResult.reject("loginFail","????????? ?????? ??????????????? ?????? ????????????.");
            return "members/loginForm";
        }
        //????????? ????????????
        //????????? ????????? ?????? ?????? ??????, ????????? ?????? ????????? ??????(???????????? true)
        HttpSession session=request.getSession(true);
        session.setAttribute(SessionConst.LOGIN_USER,loginMember);
        return "redirect:/";
    }


    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession(false);//?????? ?????? ???????????? ?????? ????????? null??????
        log.info("????????????1");
        if(session != null){
            session.invalidate();

        }
        log.info("????????????2");
        return "redirect:/";
    }

    @GetMapping("/my")
    public String my(@SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember, Model model){

        if (loginMember==null){
          return "redirect:/members/login";
        }
        model.addAttribute("member",loginMember);
        return "members/my";
    }
    @GetMapping("/myBoard")
    public String myBoard(@SessionAttribute(name=SessionConst.LOGIN_USER,required = false)Member loginMember,Model model){

        if (loginMember==null){
            return "redirect:/members/login";
        }

        List<Board> boards=boardRepository.findByMember(loginMember);


        model.addAttribute("boards",boards);
        model.addAttribute("member",loginMember);
        return "members/myBoards";
    }
    @GetMapping("/myComment")
    public String myComments(@SessionAttribute(name=SessionConst.LOGIN_USER,required = false)Member loginMember,Model model){
        if (loginMember==null){
            return "redirect:/members/login";
        }

        List<Comment> comments=commentRepository.findComments(loginMember);
        log.info("size={}",comments.size());
        model.addAttribute("comments",comments);
        model.addAttribute("member",loginMember);
        return "members/myComments";
    }

    @GetMapping("/changePwd")
    public String myChangePwd(@ModelAttribute("changePwdDto")ChangePwdDto changePwdDto) {

        return "members/changePwd";

    }

    @PostMapping("/changePwd")
    public String changePWd(@Valid  @ModelAttribute("changePwdDto")ChangePwdDto changePwdDto, BindingResult bindingResult,
                            @SessionAttribute(name=SessionConst.LOGIN_USER,required = false)Member loginMember){
        if(bindingResult.hasErrors()){
            return "members/changePwd";
        }
        if(loginMember.getPassword().equals(changePwdDto.getCur_pwd())){
                if(changePwdDto.getNew_pwd().equals(changePwdDto.getCh_new_pwd())) {
                    loginMember.setPassword(changePwdDto.getNew_pwd());
                    memberRepository.save(loginMember);
                }
                else{
                    bindingResult.reject("ChangePwdErr", "????????? ??????????????? ???????????? ?????????~");
                    return "members/changePwd";
                }
        }
        else {
            bindingResult.reject("ChangePwdErr", "?????? ??????????????? ???????????? ?????????~");
            return "members/changePwd";
        }
        return "redirect:/";


    }
    @GetMapping("/scrap")
    public String scrap(@RequestParam("id") Long id,
                        @SessionAttribute(name=SessionConst.LOGIN_USER,required = false)Member loginMember)

    {
        if (loginMember==null){
            return "redirect:/members/login";
        }
        BoardScrap boardScrap=new BoardScrap();
        Board board=boardRepository.findById(id).orElse(null);
        boardScrap.setBoard(board);
        boardScrap.setMember(loginMember);
        boardScrapRepository.save(boardScrap);
        return"redirect:/members/myScrap";
    }


    @GetMapping("/myScrap")
    public String myScrap( @SessionAttribute(name=SessionConst.LOGIN_USER,required = false)Member loginMember,Model model){

        List<BoardScrap> boards=boardScrapRepository.findBoard(loginMember);

        model.addAttribute("boards",boards);
        model.addAttribute("member",loginMember);
        return "members/myScrapboard";

    }

    @GetMapping("/findPwd")
    public String findPwd(@ModelAttribute("findPwdDto") FindPwdDto findPwdDto){
        return "members/FindPwd";
    }

    @PostMapping("/findPwd")
    public String findPwd1(@Valid @ModelAttribute("findPwdDto") FindPwdDto findPwdDto,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "members/FindPwd";
        }
        Member member=memberRepository.findByEmail(findPwdDto.getEmail()).orElse(null);
        if(member==null){
            bindingResult.reject("FindPwdErr", "???????????? ?????? ???????????????");
            return "members/FindPwd";
        }
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        String uuid= UUID.randomUUID().toString();

        member.setPassword(uuid);
        memberRepository.save(member);

        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(findPwdDto.getEmail());
        simpleMailMessage.setSubject("?????? ???????????? ??????");
        simpleMailMessage.setText("?????? ??????????????? "+uuid+"?????????. ????????? ??? ??????????????? ????????? ?????????.");

        javaMailSender.send(simpleMailMessage);
        return"redirect:/";
    }
}

