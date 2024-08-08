package com.app.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.app.entities.Movies;


public interface MovieRepository extends JpaRepository<Movies, Long> {
//	List<Movie> findByDeptId(long movieId);
}
