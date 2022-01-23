package com.board.board.repository;

import com.board.board.entity.BoardScrap;
import com.board.board.entity.ImgBoard;
import com.board.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImgBoardRepository extends JpaRepository<ImgBoard, Long> {

}
