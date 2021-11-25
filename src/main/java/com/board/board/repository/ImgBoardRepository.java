package com.board.board.repository;

import com.board.board.entity.ImgBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgBoardRepository extends JpaRepository<ImgBoard,Long> {
}
