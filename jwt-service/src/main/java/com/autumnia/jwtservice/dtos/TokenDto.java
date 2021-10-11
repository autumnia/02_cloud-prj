package com.autumnia.jwtservice.dtos;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
	
	@NotBlank
    private String token;
}
