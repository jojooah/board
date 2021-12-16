package com.board.board.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@Getter@Setter
public class CommentDto {
    @NotBlank(message = "댓글을 입력해 주세요")
    public String content;
}
