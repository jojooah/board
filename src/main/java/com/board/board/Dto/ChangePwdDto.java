package com.board.board.Dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter@Setter
public class ChangePwdDto {
    @NotBlank(message = "현재 비밀번호를 입력하세요")
    private String cur_pwd;

    @NotBlank(message = "새로운 비밀번호를 입력하세요")
    private String new_pwd;

    @NotBlank(message = "새로운 비밀번호를 입력하세요")
    private String ch_new_pwd;
}
