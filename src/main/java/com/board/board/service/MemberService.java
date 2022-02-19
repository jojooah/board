package com.board.board.service;

import com.board.board.Dto.LoginFormDto;
import com.board.board.constant.Result;
import com.board.board.constant.ResultCode;
import com.board.board.entity.Member;
import com.board.board.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MemberRepository memberRepository;


    public List<Member> getAllMember() {
        List<Member> members = memberRepository.findAll();

        return members;
    }


    public Result<Member> getMemberById(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (optionalMember.isEmpty()) {
            return ResultCode.UNKNOWN_USER.result();
        }
        Member member = optionalMember.get();
        return ResultCode.Success.result(member);
    }

    public Result<Member> login(LoginFormDto loginFormDto) {
        Optional<Member> optionalMember = memberRepository.findById(10L);
        if (optionalMember.isEmpty()) {
            return ResultCode.UNKNOWN_USER.result(null);
        }
        Member member = optionalMember.get();


        /*Optional<Member> optionalMember = memberRepository.findByEmail(loginFormDto.getEmail());
        if (optionalMember.isEmpty()) {
            return ResultCode.UNKNOWN_USER.result();
        }
        Member member = optionalMember.get();*/


        return ResultCode.Success.result(null);
    }

}