package com.jpp.web.comm;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
@Aspect
public class LoggingAOP {
   
   protected static final Logger logger = LoggerFactory.getLogger(LoggingAOP.class);
   
   @Around("execution(public * com.jpp.main.controller.*Controller.*(..))")
   public ModelAndView mavAround(ProceedingJoinPoint pjp) {
      ModelAndView mav = null;
      
      logger.info("aop works! 1");
      System.out.println("????");
      try {
         pjp.proceed();
      } catch (Throwable e) {
         
      }
      logger.info("aop works! 2");
      System.out.println("????");
      
      return mav;
   }
   
   @Around("execution(public java.lang.String com.jpp.main.controller.*Controller.*(..))")
   public String stringAround(ProceedingJoinPoint pjp)  {
      
      System.out.println("???? 1");
      logger.info("aop works!");
      try {
         pjp.proceed();
      } catch (Throwable e) {
         
      }
      logger.info("aop works!");
      System.out.println("???? 2");
      
      return "";
   }
   
}
