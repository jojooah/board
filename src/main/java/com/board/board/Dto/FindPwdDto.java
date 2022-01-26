package com.board.board.Dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotBlank;

@Getter@Setter
public class FindPwdDto {
    @NotBlank(message = "이메일을 입력해주세요")
    private String email;

}
