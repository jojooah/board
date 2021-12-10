package com.board.board.service;

import com.board.board.constant.Category;
import com.board.board.entity.Board;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;


    public List<Board> getCommu(){
        List<Board> commuss=boardRepository.findAll();
        return commuss;

    }

}
