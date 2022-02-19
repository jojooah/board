package com.board.board.controller.api;

import com.board.board.Dto.LoginFormDto;
import com.board.board.constant.Result;
import com.board.board.entity.Member;
import com.board.board.service.MemberService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/member")
public class MemberController extends AbstractController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberService memberService;

    @RequestMapping("/getMemberById/{memberId}")
    public JSONObject getMemberById(@PathVariable("memberId") Long memberId) {
        Result<Member> member = memberService.getMemberById(memberId);
        return resultJSON(member);
    }

    @PostMapping("/login")
    public @ResponseBody
    Map<String, Object> login(@RequestBody LoginFormDto loginFormDto) {
        Result<Member> member = memberService.login(loginFormDto);
        return resultMap(member);
    }

}
