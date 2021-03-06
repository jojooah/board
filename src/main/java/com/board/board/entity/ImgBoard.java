package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class ImgBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="imgBoard_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;
    private LocalDateTime updateTime;

    @Column(name="like_")
    private int like;

    @OneToMany(mappedBy = "imgBoard")
    List<Img> Imgs=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;


}
