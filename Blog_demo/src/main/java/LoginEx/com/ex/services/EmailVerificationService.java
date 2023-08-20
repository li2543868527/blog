package LoginEx.com.ex.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import LoginEx.com.ex.models.dao.EmailVerificationDao;
import LoginEx.com.ex.models.entity.EmailVerificationEntity;

import java.sql.Timestamp;

@Service
public class EmailVerificationService {

    private final EmailVerificationDao emailVerificationDao;

    @Autowired
    public EmailVerificationService(EmailVerificationDao emailVerificationDao) {
        this.emailVerificationDao = emailVerificationDao;
    }
    
    @Autowired
    private JavaMailSender javaMailSender;

    public EmailVerificationEntity generateVerificationCode(String email) {
        // 
        String verificationCode = generateRandomCode();

        // 
        EmailVerificationEntity verificationEntity = new EmailVerificationEntity();
        verificationEntity.setEmail(email);
        verificationEntity.setVerificationCode(verificationCode);
        verificationEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // 
        return emailVerificationDao.save(verificationEntity);
    }

    public boolean verifyCode(String email, String code) {
        EmailVerificationEntity verificationEntity = emailVerificationDao.findByEmail(email);

        if (verificationEntity != null && verificationEntity.getVerificationCode().equals(code)) {
            // 
            emailVerificationDao.delete(verificationEntity);
            return true;
        }
        return false;
    }
    
    public void sendVerificationCodeEmail(String email, String verificationCode) {
        String subject = "lhcホームページの認証コード";
        String text = "あなたの認証コードは：" + verificationCode;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2543868527@qq.com"); // 设置发送者地址为您的邮箱地址
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);

        javaMailSender.send(message);

    }

    private String generateRandomCode() {
    	VerificationCodeGenerator a = new VerificationCodeGenerator();
		return a.generateVerificationCode();
    }
}