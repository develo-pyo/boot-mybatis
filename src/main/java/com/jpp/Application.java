package com.jpp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application {

//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
         + "classpath:application.yml,"      // 덮어 쓸 파일(git 노출허용)
         + "classpath:application_real.yml"; // 실제파일(.gitignore 용)

	public static void main(String[] args) {

      //빌더패턴
      new SpringApplicationBuilder(Application.class)
          .properties(APPLICATION_LOCATIONS)
          .run(args);
	}
	
}
