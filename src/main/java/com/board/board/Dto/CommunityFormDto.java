package com.board.board.Dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter
public class CommunityFormDto {
    @NotBlank(message = "제목을 입력해 주세요")
    private String title;
    private Long id;
    @NotBlank(message = "내용을 입력해 주세요")
    private String content;
}
