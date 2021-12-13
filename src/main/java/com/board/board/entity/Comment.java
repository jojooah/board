package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class Comment {


    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private String content;
    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

}
