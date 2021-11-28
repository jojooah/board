package com.board.board.entity;

import com.board.board.constant.Category;
import com.board.board.constant.Level;
import com.board.board.constant.Role;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:application-test.properties")
public class EntityTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void saveBoard() {
        Member member = new Member();
        member.setEmail("123");
        member.setLevel(Level.level1);
        member.setName("member1");
        member.setRole(Role.USER);
        member.setPassword("1234");
        memberRepository.save(member);

        Board board = new Board();
        board.setCategory(Category.COMMU);
        board.setContent("gg");
        board.setLike(1);
        board.setMember(member);
        board.setTitle("title");
        board.setUpdateTime(LocalDateTime.now());
        Board savedBoard = boardRepository.save(board);

        System.out.println(savedBoard);
        Assertions.assertEquals(savedBoard.getTitle(), "title");
    }
}
