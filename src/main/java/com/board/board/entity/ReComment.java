package com.board.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ReComment {

    @Id
    @Column(name="reComment_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name="comment_id")
    private Comment comment;

    @Lob
    private String content;
    private LocalDateTime updateTime;

}
