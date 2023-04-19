package com.example.mailtemplate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mailtemplate.model.User;
import com.example.mailtemplate.service.MailService;

@RestController
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MailService mailService;

	@GetMapping("/sendtext")
	public String sendTestMail() {
		mailService.sendMail();
		return "mail sent";
	}

	@GetMapping("/sendMailUsingTemplate")
	public String sendMailWithTemplate(@RequestBody User user) {
		mailService.sendMailUsingTemplate(user);
		return "mail with template send succesfull";
	}
}
