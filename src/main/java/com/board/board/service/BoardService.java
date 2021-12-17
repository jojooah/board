package com.board.board.service;

import com.board.board.constant.Category;
import com.board.board.entity.Board;
import com.board.board.entity.Member;
import com.board.board.repository.BoardRepository;
import com.board.board.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Value("${file.dir}")
    private String fileDir;
    public String getFullPath(String filename){
        return fileDir +filename;
    }
    public String storeFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }

        String originalFilename=multipartFile.getOriginalFilename();//사용자가 업로드한 파일이름
        String uuid= UUID.randomUUID().toString();
        int pos=originalFilename.lastIndexOf(".");
        String ext=originalFilename.substring(pos+1);
        String storeFileName=uuid+"."+ext;
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return storeFileName;
    }

}
