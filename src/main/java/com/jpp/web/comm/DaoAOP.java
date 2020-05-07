package com.jpp.web.comm;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DaoAOP {
	
	protected static final Logger logger = LoggerFactory.getLogger(DaoAOP.class);
	
	/** replication 환경에서 어떤 DB session 을 사용하는지 출력 */
	@Before("within(@org.springframework.stereotype.Repository *)")
   public void befRepository(JoinPoint joinPoint) {
       Object target = joinPoint.getTarget();

       Field myBeanField = Arrays.stream(target.getClass().getDeclaredFields())
            .filter(predicate -> predicate.getType().equals(SqlSessionTemplate.class)).findFirst().orElseGet(null);

       if (myBeanField != null) {
           myBeanField.setAccessible(true);
           try {
        	  if(myBeanField.get(target) instanceof SqlSessionTemplate) {
        		  SqlSessionTemplate st = (SqlSessionTemplate) myBeanField.get(target);
        		  logger.info("connected DB[" + st.getConnection().getMetaData().getURL() + "]");
        	  }
           } catch (Exception e) {
               logger.debug("ex in dao aop");
           } 
       }
    }
	
}
