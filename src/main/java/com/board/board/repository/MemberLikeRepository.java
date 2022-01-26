package com.board.board.repository;

import com.board.board.entity.Board;
import com.board.board.entity.Member;
import com.board.board.entity.MemberLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface MemberLikeRepository extends JpaRepository<MemberLike,Long> {

    @Query("select m from MemberLike m where m.board=:board and m.member=:member")
    Optional<MemberLike> findLike(@Param("board") Board board, @Param("member") Member member);

    @Query("select m from MemberLike m where m.board=:board")
    List<MemberLike> findByBoard(@Param("board")Board board);
}
