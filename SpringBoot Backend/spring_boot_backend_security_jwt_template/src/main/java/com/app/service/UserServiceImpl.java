package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.custom_exceptions.AuthenticationException;
import com.app.dto.ApiResponse;
import com.app.dto.SigninRequest;
import com.app.dto.SigninResponse;
import com.app.dto.Signup;
import com.app.entities.UserEntity;
import com.app.repository.UserEntityRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserEntityRepository userRepository;

	@Autowired
	private ModelMapper mapper;


	@Autowired
	private PasswordEncoder encoder;

	@Override
	public Signup userRegistration(Signup reqDTO) {
		//dto --> entity
		UserEntity user=mapper.map(reqDTO, UserEntity.class);
		user.setPassword(encoder.encode(user.getPassword()));//pwd : encrypted using SHA
		return mapper.map(userRepository.save(user), Signup.class);
	}


	@Override
	public List<UserEntity> getAllUser() {


		return userRepository.findAll();
	}


	@Override
	public SigninResponse authenticateUser(SigninRequest dto) {
		// 1.invoke dao 's method
		UserEntity user = userRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword())
				.orElseThrow(() -> 
				new AuthenticationException("Fail"));
		//valid login -user : persistent -> entity -> dto
		SigninResponse sir =  mapper.map(user, SigninResponse.class);
		sir.setMessage("success");
		return sir;
	}


}
