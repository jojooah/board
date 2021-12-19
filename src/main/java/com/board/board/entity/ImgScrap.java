package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter@Setter
public class ImgScrap {

    @Id
    @Column(name="imgScrap_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="imgBoard_id")
    private ImgBoard imgBoard;
}
