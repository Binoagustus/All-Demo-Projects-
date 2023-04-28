package com.intelizign.shoppingapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intelizign.shoppingapp.exception.ShoppingException;
import com.intelizign.shoppingapp.model.User;
import com.intelizign.shoppingapp.repository.UserRepository;
import com.intelizign.shoppingapp.request.LoginRequest;
import com.intelizign.shoppingapp.response.CustomLoginResponse;
import com.intelizign.shoppingapp.utility.JwtUtils;
import com.intelizign.shoppingapp.utility.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UserRepository userRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username " + username));
		return UserDetailsImpl.build(user);
	}

	public void saveUser(User user) {
		if (userRepo.findByUsername(user.getUsername()).isPresent()) {
			throw new ShoppingException("username already exists");
		}
		if (userRepo.findByEmail(user.getEmail()).isPresent()) {
			throw new ShoppingException("Mail Id already exists");
		}
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	public CustomLoginResponse loginUser(LoginRequest request) {

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return new CustomLoginResponse(userDetails.getUserId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getPassword(),
				jwtCookie, roles);

	}
}
