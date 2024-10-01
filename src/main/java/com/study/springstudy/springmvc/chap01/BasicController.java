package com.study.springstudy.springmvc.chap01;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/spring/chap01")
public class BasicController {

    // URL: /spring/chap01/hello
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("/hello 요청");
        return "hello"; // 파일명 (접두사, 접미사는 이미 선언했잖슴~)
    }

    // ========== 요청 파라미터(Query String) 읽기 ==========

    // 1. HttpServletRequest 사용 (잘 사용X)
    // URL: /spring/chap01/person?name=hong&age=23
    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        System.out.println(name + " " + age);
        return "";
    }

    // 2. @RequestParam 사용
    // URL: /spring/chap01/major?stu=kim&major=engineering&grade=3
    @RequestMapping("/major")
    public String major(@RequestParam String stu,
                        @RequestParam("major") String mj,
                        int grade) {
        System.out.println(stu + " " + mj + " " + grade);
        return "";
    }

    // 3. 커맨드 객체 (request DTO)사용
    // URL: /spring/chap01/order?orderNum=22&goods=가방&amount=3&price=300000&.....
    @RequestMapping("/order")
    public String order(OrderDTO dto) {
        System.out.println(dto);
        return "";
    }

}
