package com.board.board.repository;

import com.board.board.entity.Board;
import com.board.board.entity.Member;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {

    @Query("select b from Board b where b.like>=:like")
    List<Board> findHot(@Param("like") int like);
}
