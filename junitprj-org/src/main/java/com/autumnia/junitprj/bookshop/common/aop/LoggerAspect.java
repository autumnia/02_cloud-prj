package com.autumnia.junitprj.bookshop.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggerAspect {

		@Around("execution(* com.autumnia.junitprj.bookshop.web.controllers.*Controller.*(..)) or " +
			"execution(* com.autumnia.junitprj.bookshop.service.*Service.*(..)) or " +
			"execution(* com.autumnia.junitprj.bookshop.domain.repository.*Repository.*(..))  " )
//	@Around("execution(* com.autumnia.shop.userservice.controllers.*Controller.*(..)) or " +
//			"execution(* com.autumnia.shop.userservice.services.impl.*Impl.*(..)) or " +
//			"execution(* com.autumnia.shop.userservice.repository.*Repository.*(..)) " )
	public Object logging(ProceedingJoinPoint pjp) throws Throwable {
		String typeName = pjp.getSignature().getDeclaringTypeName();
		String tabsize = "";

		if (typeName.indexOf("Controller") > -1) {
			tabsize = "\t";
		} else if  (typeName.indexOf("Impl") > -1) {
			tabsize = "\t\t";
		} else if  (typeName.indexOf("Service") > -1) {
			tabsize = "\t\t";
		} else if (typeName.indexOf("Repository") > -1) {
			tabsize = "\t\t\t";
		}

		log.info( tabsize + typeName + "." + pjp.getSignature().getName());
		Object result = pjp.proceed();
		log.info( tabsize + typeName + "." + pjp.getSignature().getName());

		return result;
	}
}
