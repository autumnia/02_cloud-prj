package com.autumnia.userservice.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequest {
	@NotBlank(message="이메일은 비어 있을 수 없습니다.")
	@Size(message="이메일은 최소 5자 이상입니다.")
	@Email
	private String email;
	
	@NotBlank(message="이름은 비어 있을 수 없습니다.")
	@Size(message="이름은 최소 2자 이상입니다.")	
	private String name;
	
	@NotBlank(message="비밀번호는 비어 있을 수 없습니다.")
	@Size(message="비밀번호는 최소 8자 이상입니다.")	
	private String pwd;
}
