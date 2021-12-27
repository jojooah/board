package com.board.board.repository;

import com.board.board.entity.Board;
import com.board.board.entity.Comment;
import com.board.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c where c.member=:member")
    List<Comment> findComments(@Param("member") Member member);


    @Query("select c from Comment c where c.board=:board")
    List<Comment> findByBoard(@Param("board") Board board);
}
