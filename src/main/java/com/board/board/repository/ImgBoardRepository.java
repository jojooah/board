package com.board.board.repository;

import com.board.board.entity.ImgBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgBoardRepository extends JpaRepository<ImgBoard, Long> {
}
