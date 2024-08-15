package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.app.security.JwtUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserEntityRepository userRepository;

	@Autowired
	private ModelMapper mapper;
	@Autowired
    private JwtUtils jwtUtils;

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
    private AuthenticationManager authMgr;
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
		
//		Authentication authentication = authMgr.authenticate(
//		        new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
//		    );
//		    SecurityContextHolder.getContext().setAuthentication(authentication);
//		    String jwt = jwtUtils.generateJwtToken(authentication);
//		    return new SigninResponse(jwt, "Successful Auth!!!!");
//	}
	
//	 try {
//	        Authentication authentication = authMgr.authenticate(
//	            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
//	        );
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	        String jwt = jwtUtils.generateJwtToken(authentication);
//	        return new SigninResponse(jwt, "Successful Auth!!!!");
//	    } catch (BadCredentialsException e) {
//	        // Log the exception details
//	        System.out.println("Authentication failed: " + e.getMessage());
//	        throw e;
//	    }
	 
	}


	@Override
	public SigninResponse findByemailservice(SigninRequest dto) {
		UserEntity user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> 
				new AuthenticationException("Fail"));
		//valid login -user : persistent -> entity -> dto
		SigninResponse sir =  mapper.map(user, SigninResponse.class);
		sir.setMessage("success");
		return sir;
	}


}
