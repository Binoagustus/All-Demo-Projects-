package com.intelizign.shoppingapp.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intelizign.shoppingapp.model.User;
import com.intelizign.shoppingapp.request.LoginRequest;
import com.intelizign.shoppingapp.response.CustomLoginResponse;
import com.intelizign.shoppingapp.response.ResponseHandler;
import com.intelizign.shoppingapp.response.UserLoginResponse;
import com.intelizign.shoppingapp.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@PostMapping("/registeruser")
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		try {
			userDetailsService.saveUser(user);
			return ResponseHandler.generateResponse("User registered succesfully", true, HttpStatus.OK, user);
		} catch (Exception ex) {

			logger.error("Could not register " + ex.getMessage());
			return ResponseHandler.generateResponse("User was not registered", false, HttpStatus.OK, null);
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> authenticateUser(@RequestBody LoginRequest request, HttpServletResponse response) {
		try {
			CustomLoginResponse loginResponse = userDetailsService.loginUser(request);
			response.setHeader(HttpHeaders.SET_COOKIE, loginResponse.getJwtCookie().toString());
			
			return ResponseHandler.generateResponse("User Logged In succesfully", true, HttpStatus.OK,
							new UserLoginResponse(loginResponse.getUserId(), loginResponse.getUsername(),
									loginResponse.getEmail(), loginResponse.getRoles()));
			
		} catch (Exception ex) {
			logger.error("Could not Login User " + ex.getMessage());
			return ResponseHandler.generateResponse("User was not registered", false, HttpStatus.OK, null);
		}
	}
}
