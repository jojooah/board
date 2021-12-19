package com.board.board.repository;

import com.board.board.entity.Board;
import com.board.board.entity.BoardScrap;
import com.board.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardScrapRepository extends JpaRepository<BoardScrap, Long> {

    @Query("select b from BoardScrap b where b.member=:member")
    List<BoardScrap> findBoard(@Param("member") Member member);

}
