package com.board.board.controller.board;

import com.board.board.Dto.PhotoDto;
import com.board.board.constant.SessionConst;
import com.board.board.entity.Img;
import com.board.board.entity.ImgBoard;
import com.board.board.entity.Member;
import com.board.board.repository.ImgBoardRepository;
import com.board.board.repository.ImgRepository;
import com.board.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/photo")
public class PhotoController {

    private final BoardService boardService;
    private final ImgBoardRepository imgBoardRepository;
    private final ImgRepository imgRepository;

    @RequestMapping
    public String photo(Model model, HttpServletRequest request){

        HttpSession session=request.getSession(false);//있는 세션 가져오고 만약 없으면 null반환
        if (session==null){
            return "redirect:members/login";
        }
        List<ImgBoard> imgBoards=imgBoardRepository.findAll();
      //  List<Img> imgs=imgRepository.findAll();
      //  model.addAttribute("imgs",imgs);
        model.addAttribute("imgBoards",imgBoards);
        return "photo/photoboard";
    }

    @GetMapping("/photo_form")
    public String photoForm(@ModelAttribute PhotoDto photoDto){

        return "photo/photo_form";
    }



    @PostMapping("/photo_form")
    public String photoFormPost(@ModelAttribute PhotoDto photoDto,
                                @SessionAttribute(name= SessionConst.LOGIN_USER,required = false) Member loginMember) throws IOException {


        String storedFileName=boardService.storeFile(photoDto.getImg());
        ImgBoard imgBoard=new ImgBoard();
        imgBoard.setContent(photoDto.getContent());
        imgBoard.setTitle(photoDto.getTitle());
        imgBoard.setUpdateTime(LocalDateTime.now());
        imgBoard.setMember(loginMember);
        imgBoard.setLike(0);
        imgBoardRepository.save(imgBoard);

        Img img=new Img();
        img.setImgBoard(imgBoard);
        img.setOriginUrl(photoDto.getImg().getOriginalFilename());
        img.setSavedUrl(storedFileName);
        imgRepository.save(img);

        log.info(storedFileName);


        return "redirect:/photo";
    }

    @ResponseBody
    @GetMapping("/images/{filename}")
    public Resource Image(@PathVariable String filename) throws MalformedURLException {
       return new UrlResource("file:///"+boardService.getFullPath(filename));
    }

    @GetMapping("/images/like")
    public String img_like(@RequestParam Long id){
        ImgBoard imgBoard=imgBoardRepository.findById(id).orElse(null);
        if(imgBoard!=null){
        imgBoard.setLike(imgBoard.getLike()+1);
        imgBoardRepository.save(imgBoard);
        }
        return "redirect:/photo";
    }

}
