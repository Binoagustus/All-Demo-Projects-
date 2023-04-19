package com.example.mailtemplate.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.example.mailtemplate.model.User;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailsender;

	@Autowired
	SpringTemplateEngine templateEngine;

	@Value("${spring.mail.username}")
	private String email;

	public void sendMail() {
		try {
			MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			helper.setFrom(email);
			helper.setTo(""); //give sample mail here 
			helper.setSubject("test mail");
			helper.setText("sample email using mime message helper");
			mailsender.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMailUsingTemplate(User user) {
		try {
			MimeMessage mimeMessage = mailsender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
					MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
			Context context = new Context();
			context.setVariable("user", user);
			helper.setFrom(email);
			helper.setTo(""); //give sample mail here 
			helper.setSubject("test mail");
			String html = templateEngine.process("welcome-email.html", context);
			helper.setText(html, true);
			mailsender.send(mimeMessage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
