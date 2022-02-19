package com.board.board.controller.api;

import com.board.board.Dto.LoginFormDto;
import com.board.board.constant.Result;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Member;
import com.board.board.service.MemberService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/api/member")
public class MemberController extends AbstractController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberService memberService;

    @RequestMapping("/getMemberById/{memberId}")
    public @ResponseBody
    JSONObject getMemberById(@PathVariable("memberId") Long memberId) {
        Result<Member> member = memberService.getMemberById(memberId);
        return resultJSON(member);
    }

    @PostMapping("/login")
    public @ResponseBody
    Map<String, Object> login(@RequestBody LoginFormDto loginFormDto, HttpServletRequest request) {
        Result<Member> memberResult = memberService.login(loginFormDto);
        if (memberResult.isSuccess()) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SessionConst.LOGIN_USER, memberResult.getResultObject());
        }
        return resultMapCodeAndMsg(memberResult);
    }

}
