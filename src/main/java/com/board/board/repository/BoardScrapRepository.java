package com.board.board.repository;

import com.board.board.entity.BoardScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {

}
