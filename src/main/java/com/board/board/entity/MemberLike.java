package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Setter
@Getter
public class MemberLike {

    @Id
    @Column(name="boardScrap_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;
}
