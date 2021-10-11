package com.autumnia.jwtservice.jwts;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, 
			HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
		// 필요한 권한이 존재하지 않는 경우403 fobbiden errror 발생
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
}
