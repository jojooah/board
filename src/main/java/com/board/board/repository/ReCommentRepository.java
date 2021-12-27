package com.board.board.repository;

import com.board.board.entity.Board;
import com.board.board.entity.Comment;
import com.board.board.entity.ReComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReCommentRepository extends JpaRepository<ReComment, Long> {

    @Query("select c from ReComment c where c.comment=:comment")
    List<ReComment> findByComment(@Param("comment") Comment comment);
}
