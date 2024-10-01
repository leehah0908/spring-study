package com.study.springstudy.core.chap04.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 지정한 패키지 내에 있는 @Component 붙은 객체들을 전부 스캔해서 스프링 컨테이너에 등록시키겠다.
// Hotel 클래스가 많으니까 뒤에 chap04까지 붙어야 정상 작동함
@ComponentScan(basePackages = "com.study.springstudy.core.chap04")
public class AppConfig {

}
