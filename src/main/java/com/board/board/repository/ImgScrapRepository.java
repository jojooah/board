package com.board.board.repository;

import com.board.board.entity.ImgScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgScrapRepository extends JpaRepository<ImgScrap, Long> {
}
