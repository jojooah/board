package com.board.board.controller.board;

import com.board.board.Dto.CommentDto;
import com.board.board.Dto.CommunityFormDto;
import com.board.board.constant.Category;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Board;
import com.board.board.entity.Comment;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.MemberRepository;
import com.board.board.service.BoardService;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final CommentRepository commentRepository;
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
        List<Comment> comments = board.getComments();
        CommentDto commentDto=new CommentDto();
        if(board==null){
            return "redirect:/board/community";

        }
        else{
            model.addAttribute("board",board);
            model.addAttribute("comments",comments);
            model.addAttribute("commentForm",commentDto);
            log.info("츄라이츄라이");
            return "board/content";
        }


    }
    @RequestMapping("/commu_delete")
    public String comDelete(@RequestParam Long id){
        if(id==null){return "redirect:/board/community";}

        Board board=boardRepository.findById(id).orElse(null);

        if(board!=null){
            boardRepository.delete(board);

        }

        return "redirect:/board/community";

    }

    @RequestMapping("/commu_like")
    public String commu_like(@RequestParam Long id){
        if(id==null) return "redirect:/board/community";

        Board board=boardRepository.findById(id).orElse(null);
        if(board!=null){
            board.setLike(board.getLike()+1);

            boardRepository.save(board);
        }

        return "redirect:/board/community";
    }

    @PostMapping("/commu/comment")
    public String comment(@Valid @ModelAttribute CommentDto commentDto, BindingResult bindingResult, @RequestParam Long id,
                          @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember,
                          RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            return "board/content";
        }
        Board board=boardRepository.findById(id).orElse(null);
        if (board == null){
            return "redirect:/board/community";
        }

        log.info(commentDto.getContent());
        Comment comment=new Comment();
        comment.setContent(commentDto.getContent());
        comment.setBoard(board);
        comment.setMember(loginMember);
        commentRepository.save(comment);
        redirectAttributes.addAttribute("id",id);

        return "redirect:/board/content";
    }

}
