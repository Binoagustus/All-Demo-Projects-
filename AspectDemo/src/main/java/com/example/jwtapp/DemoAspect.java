package com.example.jwtapp;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy
@Component
@Aspect
public class DemoAspect {
/*
	@Before("execution(* com.example.jwtapp.controller.SampleController.getMessage(..))")
	public void executeBeforeMethod() {
		System.out.println("before executing controller mwthod");
	}
*/	
	@After("execution(* com.example.jwtapp.controller.SampleController.getMessage(int, int))")
	public void executeAfterMethod() {
		System.out.println("after executing controller method");
	}
	
/*	
	@Around("execution(* com.example.jwtapp.controller.SampleController.getMessage(..))")
	public int executeAroundMethod(JoinPoint joinPoint) {
		int a = Integer.parseInt(joinPoint.getArgs()[0].toString());
		return a + 10;
	}
*/
//	@AfterReturning(pointcut="execution(* com.example.jwtapp.controller.SampleController.getMessage(..))", returning="retVal")
//	public void afterReturningAdvice(Object retVal) {
//		int c = 25 + Integer.parseInt(retVal.toString());
//		System.out.println("After returning c became " + c);
//	}
}
