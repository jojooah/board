package com.board.board.service;

import com.board.board.constant.Result;
import com.board.board.constant.ResultCode;
import com.board.board.entity.Member;
import com.board.board.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member == null) {
            return new Result<>(member, ResultCode.UNKNOWN_USER);
        }
        return new Result<>(member, ResultCode.Success);
    }

}