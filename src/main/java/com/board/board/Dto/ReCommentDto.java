package com.board.board.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter
public class ReCommentDto {
    @NotBlank(message = "대댓글을 입력해 주세요")
    public String content;
}
