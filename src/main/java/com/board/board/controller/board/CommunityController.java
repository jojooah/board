package com.board.board.controller.board;

import com.board.board.Dto.CommunityFormDto;
import com.board.board.constant.Category;
import com.board.board.entity.Board;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.MemberRepository;
import com.board.board.service.BoardService;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommunityController {

    private final BoardRepository boardRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @RequestMapping("/community")
    public String community(Model model){

        List<Board> boards=boardRepository.findAll();

        model.addAttribute("boards",boards);

        return "board/community";
    }

    @RequestMapping("/commu_form")
    public String commuForm(@RequestParam(required = false) Long id, Model model){
        CommunityFormDto commu=new CommunityFormDto();
        if (id!=null)
        {
            Board board=boardRepository.findById(id).orElse(null);
            if (board==null){
                return  "redirect:/board/community";
            }

            commu.setId(board.getId());
            commu.setTitle(board.getTitle());
            commu.setContent(board.getContent());


        }

        model.addAttribute("Form",commu);
        return "board/commu_form";


    }
    @PostMapping("/commu_form")
    public String commuFormPost(@Valid @ModelAttribute CommunityFormDto communityFormDto, @RequestParam(required = false) Long id, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "board/commu_form";
        }
        if (id==null)
        {
            Board board=new Board();

            board.setTitle(communityFormDto.getTitle());
            board.setContent(communityFormDto.getContent());
            board.setCategory(Category.COMMU);
            board.setLike(0);
            boardRepository.save(board);
        }
        else
        {
            Board board=boardRepository.findById(id).orElse(null);
            if(board==null){
                return "redirect:/board/community";
            }
            board.setContent(communityFormDto.getContent());
            board.setTitle(communityFormDto.getTitle());
            boardRepository.save(board);

        }
        return "redirect:/board/community";
    }

    @RequestMapping("/content")
    public String content(@RequestParam Long id, Model model){
        if(id==null){ return "redirect:/board/community";}

        Board board=boardRepository.findById(id).orElse(null);
        if(board==null){
            return "redirect:/board/community";

        }
        else{
            model.addAttribute("board",board);
            log.info("츄라이츄라이");
            return "board/content";
        }
    }



}
