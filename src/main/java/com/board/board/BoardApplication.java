package com.board.board;

import com.board.board.entity.Board;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class BoardApplication {

	public static void main(String[] args) {



		SpringApplication.run(BoardApplication.class, args);
	}

}
