package com.board.board.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
public class Comment {


    @Id
    @Column(name="comment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    @JsonBackReference
    private Member member;

    private String content;
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "comment")
    @JsonManagedReference
    List<ReComment> Recomments=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="board_id")
    @JsonBackReference
    private Board board;

}
