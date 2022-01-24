package com.board.board.controller.board;

import com.board.board.Dto.CommentDto;
import com.board.board.Dto.CommunityFormDto;
import com.board.board.Dto.ReCommentDto;
import com.board.board.constant.Category;
import com.board.board.constant.SessionConst;
import com.board.board.entity.*;
import com.board.board.repository.*;
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
    private final MemberLikeRepository memberLikeRepository;

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
    public String content(@RequestParam Long id, Model model,
                          @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember){
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
            if(board.getCategory().equals(Category.COMMU)){
                model.addAttribute("title","commu");
            }
            else if(board.getCategory().equals(Category.BOOK)){
                model.addAttribute("title","book");
            }



            if(loginMember != null){
                MemberLike memberLike=memberLikeRepository.findLike(board,loginMember).orElse(null);
                if (memberLike != null){
                    model.addAttribute("like",3); //이미 좋아요 눌렀어요
                }
                else{
                    model.addAttribute("like",1);//좋아요가능
                }
            }
            else{
                model.addAttribute("like",2);//로그인해주세요
            }


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
    @ResponseBody
    public void commu_like(@RequestParam Long id,
                          @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember){
        //if(id==null) return "redirect:/";


        int like=0;
        Board board=boardRepository.findById(id).orElse(null);


        if(board!=null && loginMember != null){
            MemberLike memberLike=new MemberLike();
            board.setLike(board.getLike()+1);
            memberLike.setMember(loginMember);
            memberLike.setBoard(board);
            memberLikeRepository.save(memberLike);
            boardRepository.save(board);

        }

    }

    @PostMapping("/commu/comment")
    public String comment(@Valid @ModelAttribute CommentDto commentForm, BindingResult bindingResult, @RequestParam Long id,
                          @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember,
                          RedirectAttributes redirectAttributes,Model model){

        Board board=boardRepository.findById(id).orElse(null);
        if (bindingResult.hasErrors()){
            model.addAttribute("board",board);
            return "board/content";
        }

        if (board == null){

            return "redirect:/";
        }

        log.info(commentForm.getContent());
        Comment comment=new Comment();
        comment.setContent(commentForm.getContent());
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
    public String book(Model model, @PageableDefault(size = 10) Pageable pageable){

        Page<Board> boards=boardRepository.findByCate(Category.BOOK,pageable);
        int startPage= Math.max(1,boards.getPageable().getPageNumber()-4);
        int endPage=Math.min(boards.getTotalPages(),boards.getPageable().getPageNumber()+4);

        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
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
    public String bookFormPost(@RequestParam(required = false) Long id,
                               @Valid @ModelAttribute CommunityFormDto communityFormDto,
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
            board.setCategory(Category.BOOK);
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
