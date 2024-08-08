package com.app.service;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.MovieDTO;
import com.app.entities.Movies;
import com.app.repository.MovieRepository;


@Service
@Transactional
public class MovieServiceImpl implements MovieService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private MovieRepository movieRepo;
	@Autowired
	private ImageHandlingService imgHandlingService;

	
	@Override
	public MovieDTO addNewMovieWithImage(MovieDTO dto, MultipartFile image) throws IOException {
		// validate confirm password
//				if (dto.getPassword().equals(dto.getConfirmPassword())) {
					// validate dept id
					//Department dept = deptRepo.findById(dto.getDeptId())
//							.orElseThrow(() -> new ResourceNotFoundException("Invalid Dept Id!!!"));
					Movies movie = mapper.map(dto, Movies.class);
					// upload image , set image path to emp.
					imgHandlingService.uploadImage(movie, image);
					//dept.addEmployee(empEntity);
					Movies savedMovie = movieRepo.save(movie);// Actually not needed by hibernate BUT to get persistent emp id
																// n to send to clnt doing this !
					// System.out.println("emp entity id " + empEntity.getId() + " " +
					// savedEmp.getId());

					return mapper.map(savedMovie, MovieDTO.class);

				
//				throw new ApiException("Passwords don't match");
	}


	 @Override
	    public MovieDTO get(Long movieId) {
	        Movies movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found of given Id !!"));
	        return mapper.map(movie, MovieDTO.class);
	    }
	 
	 @Override
	    public MovieDTO update(MovieDTO movieDto, Long movieId) {

	        //fetch the Movie of given id
	        Movies Movie = movieRepo.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie not found of given Id !!"));
	        Movie.setMName(movieDto.getMName());
	        Movie.setMDescription(movieDto.getMDesc());
	        Movie.setMRating(movieDto.getRating());
//	      
	        Movie.setMovieImageName(movieDto.getMovieImageName());

//	        save the entity
	        Movies updatedMovie = movieRepo.save(Movie);
	        return mapper.map(updatedMovie, MovieDTO.class);
	    }
	

}
