// ShowtimesController.java
package com.app.controller;

import com.app.dto.SeatDTO;
import com.app.entities.SeatEntity;
import com.app.service.ShowtimesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
public class ShowtimesController {

    @Autowired
    private ShowtimesService showtimesService;

    @GetMapping("/{movieId}/{showtimeId}/seats")
    public ResponseEntity<List<SeatDTO>> getSeatsForShowtime(@PathVariable Long movieId, @PathVariable Long showtimeId) {
        List<SeatDTO> seats = showtimesService.getSeatsForShowtime(movieId, showtimeId);
        return ResponseEntity.ok(seats);
    }
}




