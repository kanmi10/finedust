package com.project.dust.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private String authNum;

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to); // 받는 사람
        message.setSubject("회원가입 이메일 인증");

        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='background-color: #000000 !important; margin: 0 auto; max-width: 600px; word-break: break-all; padding-top: 50px; color: #ffffff;'>");
        sb.append("<h1 style='padding-top: 50px; font-size: 30px;'>이메일 주소 인증</h1>");
        sb.append("<p style='padding-top: 20px; font-size: 18px; opacity: 0.6; line-height: 30px; font-weight: 400;'>안녕하세요?<br/>");
        sb.append("오늘미세어때 서비스 사용을 위해 회원가입시 고객님께서 입력하신 이메일 주소의 인증이 필요합니다.<br/>");
        sb.append("하단의 인증 번호로 이메일 인증을 완료하시면, 정상적으로 오늘미세어때 서비스를 이용하실 수 있습니다.<br/>");
        sb.append("감사합니다.</p>");
        sb.append("<div class='code-box' style='margin-top: 50px; padding-top: 20px; color: #000000; padding-bottom: 20px; font-size: 25px; text-align: center; background-color: #f4f4f4; border-radius: 10px;'>").append(authNum).append("</div>");
        sb.append("</body></html>");

        message.setText(sb.toString(), "utf-8", "html");
        message.setFrom(new InternetAddress("noreply.todaydust@gamil.com", "오늘미세어때?"));

        return message;
    }

    public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) { // 총 8자리 인증 번호 생성
            int idx = random.nextInt(3); // 0~2 사이의 랜던 값

            // 0,1,2 랜덤값 변환
            // 숫자와 ASCII 코드를 이용
            switch (idx) {
                case 0 :
                    // 0일 때, a~z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 97));
                    break;
                case 1:
                    // 1일 때, A~Z 까지 랜덤 생성 후 key에 추가
                    key.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    // 2일 때, 0~9 까지 랜덤 생성 후 key에 추가
                    key.append(random.nextInt(9));
                    break;
            }
        }

        return authNum = key.toString();
    }

    //메일 발송
    //등록해둔 javaMail 객체를 사용해서 이메일 send
    public String sendSimpleMessage(String sendEmail) throws MessagingException, UnsupportedEncodingException {
        authNum = createCode();
        log.info("생성 인증코드: {}", authNum);

        MimeMessage message = createMessage(sendEmail);

        try {
            javaMailSender.send(message); // 메일 발송
        } catch (MailException e) {
            log.error("메일 에러", e);
            throw e;
        }
        return authNum;
    }

}
