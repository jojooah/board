package com.board.board.service;

import com.board.board.entity.Member;
import com.board.board.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    MemberRepository memberRepository;

    public Member Login(String email,String password){
        Optional<Member> memberOptional=memberRepository.findByEmail(email);

        if(memberOptional.isPresent()){
            Member findMember= memberOptional.get();

            if(findMember.getPassword().equals(password)){
                return findMember;
            }
        }
        return null;
    }


}
