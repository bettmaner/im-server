package edu.ncu.zww.imserver.service.tools;

import edu.ncu.zww.imserver.common.util.EmailType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") private String from;

    @Override
    public void sendMail(String to, EmailType type) throws MessagingException {
        System.out.println("准备执行发送任务"+EmailType.REGISTER.getName());
        //System.out.println();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(type.getName());

        switch (type) {
            case REGISTER:
                helper.setText(getRegisterContent(), true);
                break;
            case FORGET:
                helper.setText(getForgetContent(), true);
                break;
            default:
        }
        mailSender.send(mimeMessage);
    }

    // 随机生成指定number位数验证码
    private String createCode(Integer number){
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();   // 随机用以下三个随机生成器
        Random randdata = new Random();
        int data = 0;
        for(int i=0;i<number;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        return sb.toString();
    }


    // 注册邮件内容
    public String getRegisterContent() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h1 style='color: #03A9F4'>WEI_IM：</h1>\n" +
                "    <hr>" +
                "    <p>欢迎使用WEI_IM通讯app </p>" +
                "    <p>您的账号已注册成功，账号：, 初始密码："+createCode(4) +"</p>" +
                "    <p>为了您的信息安全，请保管好您的账号密码，并登录后重置密码。</p>"  +
                "</body>\n" +
                "</html>";
        return content;
    }

    // 重置密码邮件内容
    private String getForgetContent() {
        String content = "<html>\n" +
                "<body>\n" +
                "    <h1 style='color: #03A9F4'>WEI_IM：</h1>\n" +
                "    <hr>" +
                "    <p>您的WEI_IM密码重置成功！ </p>" +
                "</body>\n" +
                "</html>";
        return content;
    }
}
