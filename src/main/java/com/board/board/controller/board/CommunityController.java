package com.board.board.controller.board;

import com.board.board.Dto.CommentDto;
import com.board.board.Dto.CommunityFormDto;
import com.board.board.Dto.ReCommentDto;
import com.board.board.constant.Category;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Board;
import com.board.board.entity.Comment;
import com.board.board.entity.Member;
import com.board.board.entity.ReComment;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.CommentRepository;
import com.board.board.repository.MemberRepository;
import com.board.board.repository.ReCommentRepository;
import com.board.board.service.BoardService;
import com.board.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    private final ReCommentRepository reCommentRepository;
    @RequestMapping("/community")
    public String community(Model model, @PageableDefault(size = 10) Pageable pageable){

        Page<Board> boards=boardRepository.findByCate(Category.COMMU,pageable);
        int startPage= Math.max(1,boards.getPageable().getPageNumber()-4);
        int endPage=Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards",boards);

        return "board/community";
    }

    @GetMapping("/commu_form")
    public String commuForm(@RequestParam(required = false) Long id, Model model){
        CommunityFormDto commu=new CommunityFormDto();
        if (id!=null) //수정
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
    public String commuFormPost(@Valid @ModelAttribute CommunityFormDto communityFormDto, @RequestParam(required = false) Long id,
                                BindingResult bindingResult,
                                @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember ){
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
            if (loginMember!=null){
                board.setMember(loginMember);
            }
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

    @GetMapping("/content")
    public String content(@RequestParam Long id, Model model){
        if(id==null){ return "redirect:/board/community";}

        Board board=boardRepository.findById(id).orElse(null);
        List<Comment> comments = board.getComments();
        CommentDto commentDto=new CommentDto();
        ReCommentDto reCommentDto=new ReCommentDto();
        if(board==null){
            return "redirect:/board/community";

        }
        else{
            model.addAttribute("board",board);
            model.addAttribute("comments",comments);
            model.addAttribute("commentForm",commentDto);
            model.addAttribute("recommentForm",reCommentDto);
            log.info("츄라이츄라이");
            return "board/content";
        }


    }
    @GetMapping("/commu_delete")
    public String comDelete(@RequestParam Long id){
        if(id==null){return "redirect:/board/community";}

        Board board=boardRepository.findById(id).orElse(null);

        if(board!=null){
            List<Comment> comments=commentRepository.findByBoard(board);

            for (Comment comment:comments){

                List<ReComment> reComments=reCommentRepository.findByComment(comment);
                for (ReComment reComment : reComments){
                    reCommentRepository.delete(reComment);
                }
                commentRepository.delete(comment);
            }

            boardRepository.delete(board);

        }

        return "redirect:/board/community";

    }

    @GetMapping("/commu_like")
    public String commu_like(@RequestParam Long id){
        if(id==null) return "redirect:/";

        Board board=boardRepository.findById(id).orElse(null);
        if(board!=null){
            board.setLike(board.getLike()+1);

            boardRepository.save(board);
        }
        if(board.getCategory().equals(Category.COMMU)){
           return "redirect:/board/community"; }
        else if(board.getCategory().equals(Category.BOOK)){
            return"redirect:/board/book"; }
        else
            return "redirect:/";
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

            return "redirect:/";
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
    @PostMapping("/commu/recomment")
    public String recomment(@Valid @ModelAttribute ReCommentDto recommentDto, BindingResult bindingResult,
                            @RequestParam("id") Long id,@RequestParam("contentId") Long contentId,
                            @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember,
                            RedirectAttributes redirectAttributes){
        Comment comment=commentRepository.findById(id).orElse(null);
        if(comment==null){return "board/content";}
        if(bindingResult.hasErrors()){
            return "board/content";
        }
        log.info("z");
        ReComment reComment=new ReComment();
        reComment.setContent(recommentDto.getContent());
        reComment.setMember(loginMember);
        reComment.setUpdateTime(LocalDateTime.now());
        reComment.setComment(comment);
        reCommentRepository.save(reComment);
        redirectAttributes.addAttribute("id",contentId);

        return "redirect:/board/content";
    }

    @GetMapping("/book")
    public String book(Model model){
        Page<Board> boards=boardRepository.findByCate(Category.BOOK,PageRequest.of(0,20));
        model.addAttribute("boards",boards);
        return "bookboard/book";
    }

    @GetMapping("/book_form")
    public String bookForm(@RequestParam(required = false) Long id,
                           Model model){
        CommunityFormDto bookFormDto=new CommunityFormDto();
        if (id!=null)//수정
        {
            Board board=boardRepository.findById(id).orElse(null);
            if (board==null){
                return  "redirect:/board/book";
            }

            bookFormDto.setId(board.getId());
            bookFormDto.setTitle(board.getTitle());
            bookFormDto.setContent(board.getContent());


        }
        bookFormDto.setCategory(Category.BOOK);
        model.addAttribute("Form",bookFormDto);
        return "bookboard/book_form";


    }
    @PostMapping("/book_form")
    public String bookFormPost(@Valid @ModelAttribute CommunityFormDto communityFormDto,
                               @RequestParam(required = false) Long id,
                                BindingResult bindingResult,
                                @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember ){
        if(bindingResult.hasErrors()){
            return "board/book_form";
        }
        if (id==null)
        {
            Board board=new Board();

            board.setTitle(communityFormDto.getTitle());
            board.setContent(communityFormDto.getContent());
            board.setCategory(communityFormDto.getCategory());
            board.setLike(0);
            if (loginMember!=null){
                board.setMember(loginMember);
            }
            boardRepository.save(board);
        }
        else
        {
            Board board=boardRepository.findById(id).orElse(null);
            if(board==null){
                return "redirect:/board/book";
            }
            board.setContent(communityFormDto.getContent());
            board.setTitle(communityFormDto.getTitle());
            boardRepository.save(board);

        }
        return "redirect:/board/book";
    }


}
