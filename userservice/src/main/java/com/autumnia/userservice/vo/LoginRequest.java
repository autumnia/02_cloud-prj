package com.autumnia.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginRequest {
	@NotBlank(message="이메일은 비어 있을 수 없습니다.")
	@Size(min=5, message="이메일은 최소 5자 이상입니다.")
	@Email
	private String email;
	
	@NotBlank(message="비밀번호는 비어 있을 수 없습니다.")
	@Size(min=8, message="비밀번호는 최소 8자 이상입니다.")	
	private String pwd;
}
