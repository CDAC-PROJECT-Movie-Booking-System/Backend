package com.app.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.ImageResponse;
import com.app.dto.MovieDTO;
import com.app.service.FileService;
import com.app.service.MovieService;

@RestController
@RequestMapping("/moviestest")
public class MoviesImageTestController {
	@Autowired
	private MovieService mservice;
	@Autowired
    private FileService fileService;

    @Value("${product.image.path}")
    private String imagePath;
    
  //upload image
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/image/{movieId}")
    public ResponseEntity<ImageResponse> uploadMovieImage(
            @PathVariable Long movieId,
            @RequestParam("movieImage") MultipartFile image
    ) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        MovieDTO movieDto = mservice.get(movieId);
        movieDto.setMovieImageName(fileName);
        MovieDTO updatedProduct = mservice.update(movieDto,movieId);
        ImageResponse response = ImageResponse.builder().imageName(updatedProduct.getMovieImageName()).message("Movie image is successfully uploaded !!").status(HttpStatus.CREATED).success(true).build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }


    //serve image

    @GetMapping(value = "/image/{productId}")
    public void serveUserImage(@PathVariable Long productId, HttpServletResponse response) throws IOException {
    	MovieDTO movieDto = mservice.get(productId);
        InputStream resource = fileService.getResource(imagePath, movieDto.getMovieImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());

    }
    
}
