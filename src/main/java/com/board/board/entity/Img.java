package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
public class Img {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="img_id")
    private Long id;

    private String savedUrl;
    private String originUrl;

    @ManyToOne
    @JoinColumn(name="imgBoard_id")
    private ImgBoard imgBoard;

    private String thumbNailYn; //썸네일 이미지 여부
}
