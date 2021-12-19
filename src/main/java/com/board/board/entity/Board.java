package com.board.board.entity;

import com.board.board.constant.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter@ToString
public class Board {
    @Id
    @Column(name="board_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @Lob
    private String content;
    private LocalDateTime updateTime;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Comment> comments=new ArrayList<>();

    @Column(name="like_")
    private int like;
}
