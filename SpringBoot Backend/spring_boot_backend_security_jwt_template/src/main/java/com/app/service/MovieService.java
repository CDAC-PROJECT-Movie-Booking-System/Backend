package com.app.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.MovieDTO;


public interface MovieService {

	
	MovieDTO addNewMovieWithImage(MovieDTO dto, MultipartFile image) throws IOException;
	
	MovieDTO get(Long productId);

	MovieDTO update(MovieDTO movieDto, Long movieId);
	
//	MovieDTO update(MovieDTO movieDto, Long movieId)
}
