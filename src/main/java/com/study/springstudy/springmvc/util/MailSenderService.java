package com.study.springstudy.springmvc.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailSenderService {

    // EmailConfig에 선언한 객체가 주입 가능
    private final JavaMailSender mailSender;

    // 난수 발생
    private int makeRandomNumber() {
        // 난수의 범휘: 111111 ~ 999999 (6자리)
        return (int) ((Math.random() * 888889) + 111111);
    }

    // 가입할 회원에게 전송할 컨트롤러용 이메일 양식 준비
    public String joinMail(String email) throws MessagingException {
        int checkNum = makeRandomNumber();
        String setFrom = "leehah0908@gmail.com";
        String toMail = email;
        String title = "이메일 중복 확인 메일입니다.";
        String content = "홈페이지 가입을 신청해 주셔서 감사합니다." +
                "<br><br>" +
                "인증 번호는 <strong>" + checkNum + "</strong> 입니다. <br>" +
                "해당 인증 번호를 인증번호 확인란에 기입해 주세요."; // 이메일에 삽입할 내용 (더 꾸며보세요)

        mailSend(setFrom, toMail, title, content);

        return Integer.toString(checkNum);
    }

    private void mailSend(String setFrom, String toMail, String title, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
       /*
       기타 설정들을 담당할 MimeMessageHelper 객체를 생성
       생성자의 매개값으로 MimeMessage 객체, bool, 문자 인코딩 설정
       true 매개값을 전달하면 MultiPart 형식의 메세지 전달이 가능 (첨부 파일)
       */

        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setFrom(setFrom);
        System.out.println("===== 보내느 사ㅏㅁ 완료 =====");
        helper.setTo(toMail);
        System.out.println("===== 받는 ㅅㄹ암 완료 =====");
        helper.setSubject(title);
        System.out.println("===== 제목 완료 =====");
        helper.setText(content, true); // html이 포함되어 있으면 true
        System.out.println("===== 내용 요ㅏ노 =====");

        mailSender.send(message);

    }
}
