package com.jpp.web.comm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Aspect
public class LoggingAOP {
   
   protected static final Logger logger = LoggerFactory.getLogger(LoggingAOP.class);
   
   private static final String CANNOT_PRINT_INPARAM = "{IN_PARAMS LENGTH IS TOO LONG TO PRINT}";
   private static final String CANNOT_PRINT_OUTPARAM = "{OUT_PARAMS LENGTH IS TOO LONG TO PRINT}";
   private static final String CANNOT_PRINT_VALUE = "VALUE LENGTH IS TOO LONG TO PRINT";
   private static final String NOTHING_TO_PRINT = "RETURN TYPE IS VOID";
   
   @Around("execution(public * com.jpp.main.controller.*Controller.*(..))")
   public Object mavAround(ProceedingJoinPoint pjp) {
      Object result = null;
      String inputParam = "";
      
      HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
      
      for (Object obj : pjp.getArgs()) {
         
         if (obj instanceof Map) {
            Map<String, Object> tmpMap = new HashMap<String, Object>(); 
            
            for(Map.Entry<String, Object> entry : ((Map<String, Object>) obj).entrySet()){
               if(entry.getValue().toString().length() < 1500){
                  tmpMap.put(entry.getKey(), entry.getValue());
               } else {
                  tmpMap.put(entry.getKey(), CANNOT_PRINT_VALUE);
               }
            }

            inputParam = tmpMap.toString();

         }  else if (obj instanceof String){
            
            try {
               Map<String, Object> tmpMap = new HashMap<String, Object>();
               Map<String, Object> tmpMap2 = new ObjectMapper().readValue((String)obj, Map.class);
               
               for(Map.Entry<String, Object> entry : tmpMap2.entrySet()){
                  if(entry.getValue().toString().length() < 1500){
                     tmpMap.put(entry.getKey(), entry.getValue());
                  } else {
                     tmpMap.put(entry.getKey(), CANNOT_PRINT_VALUE);
                  }
               }

               inputParam = tmpMap.toString();
               
            } catch (JsonParseException e) {
               logger.debug("exception during parsing input param : {}", e);
            } catch (JsonMappingException e) {
               logger.debug("exception during parsing input param : {}", e);
            } catch (Exception e) {
               logger.debug("exception during parsing input param : {}", e);
            }
            
         }
      }

      long start = System.currentTimeMillis();

      String controller = (String) pjp.getTarget().getClass().getSimpleName();

      String path = request.getRequestURI();
      String addr = request.getRemoteAddr();
      int port = request.getRemotePort();
            
      logger.info("##########################################################################");
      logger.info("# REQUEST | CONTROLLER = {} | METHOD = {} | REMOTEADDR = {} | PORT = {} | IN_PARAMS = {}",
            controller, path, addr, port, inputParam == null ? "": (inputParam.length()>=1500?CANNOT_PRINT_INPARAM:inputParam));
      logger.info("##########################################################################");
      
      int resultCode = 200;
      try {
         result = pjp.proceed();
         if(result instanceof ResponseEntity) {
            ResponseEntity re = (ResponseEntity)result;
            resultCode = re.getStatusCodeValue();
         }
         return result;
      } catch (Throwable e) {
         
         if(result instanceof ResponseEntity) {
            ResponseEntity re = (ResponseEntity)result;
            resultCode = re.getStatusCodeValue();
         }
         
         logger.error("error : {}", e);
      } finally {
         String outParam = "";
         if (result instanceof ModelAndView) {
            Map<String, Object> resultMap = (Map<String, Object>)result;
            Map<String, Object> tmpMap = new HashMap<String, Object>();
            
            for(Map.Entry<String, Object> entry : resultMap.entrySet()){
               if(entry.getValue().toString().length() < 1500){
                  tmpMap.put(entry.getKey(), entry.getValue());
               } else {
                  tmpMap.put(entry.getKey(), CANNOT_PRINT_VALUE);
               }
            }
            outParam = tmpMap.toString();
         } else if(result instanceof String){
            outParam = (String) result;
         } 
         
         long end = System.currentTimeMillis();
         logger.info("##########################################################################");
         logger.info("# RESPONSE | CONTROLLER = {} | METHOD = {} | RESULT_CODE = {} | REMOTEADDR = {} | PORT = {} | TIME = {} ms | IN_PARAMS = {} | OUT_PARAMS = {}",
               controller, path, resultCode, addr, port, end - start,
               inputParam==null?"":(inputParam.length()>=1500?CANNOT_PRINT_INPARAM:inputParam), 
               outParam);
         logger.info("##########################################################################");
         
         
      }
      logger.info("aop works! 2");
      
      return result;
   }
   
   
}
