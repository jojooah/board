package com.board.board.controller.board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import com.board.board.constant.Category;
import com.board.board.entity.Board;
import com.board.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public String hotBoard(Model model,@PageableDefault(size = 10) Pageable pageable){

        Page<Board> boards=boardRepository.findHot(3,pageable);
        int startPage= Math.max(1,boards.getPageable().getPageNumber()-4);
        int endPage=Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);

        return "hotboard/hotboard";
    }



}
