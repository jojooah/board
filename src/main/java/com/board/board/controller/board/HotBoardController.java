package com.board.board.controller.board;

import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/hot")
public class HotBoardController {
    public final BoardRepository boardRepository;

    @RequestMapping
    public String hotBoard(Model model){
        List<Board> boards=boardRepository.findHot(3);
        model.addAttribute("boards",boards);
        return "hotboard/hotboard";
    }

}
