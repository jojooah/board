package com.board.board.entity;

import com.board.board.constant.Level;
import com.board.board.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
   private Long id;

    @OneToMany(mappedBy = "member",fetch = FetchType.EAGER)
    private List<Board> boards=new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments=new ArrayList<>();

    @Column(unique = true)
    private String name;

    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Level level;

    @Enumerated(EnumType.STRING)
    private Role role;

}
