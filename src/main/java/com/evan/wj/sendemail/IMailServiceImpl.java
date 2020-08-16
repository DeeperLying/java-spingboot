package com.evan.wj.sendemail;

import com.evan.wj.utils.EmailCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

@CacheConfig(cacheNames = "email")
@Service
public class IMailServiceImpl implements IMailService {
    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 简单文本邮件
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public String sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        String code = EmailCodeUtils.getNumber();
        redisTemplate.opsForValue().set("code", code, 120, TimeUnit.SECONDS);
        //邮件发送人
        try {
            message.setFrom(from);
            //邮件接收人
            message.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            message.setText(content + code);
            //发送邮件
            mailSender.send(message);
            System.out.print("邮件已经发送。");
        } catch (Exception e) {
            System.out.print("发送邮件时发生异常！" + e.toString());
        }
        return "success";
    }
}
