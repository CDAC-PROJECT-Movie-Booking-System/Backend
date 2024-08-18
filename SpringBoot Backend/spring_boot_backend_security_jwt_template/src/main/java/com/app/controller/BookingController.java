package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.BookingRequest;
import com.app.entities.BookingEntity;
import com.app.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping()
    public ResponseEntity<BookingEntity> bookSeats(@RequestBody BookingRequest bookingRequest) {
        Long userId = bookingRequest.getUserId();
        List<Long> id = bookingRequest.getId();
        Long movieId = bookingRequest.getMovieId();
        Long showtimeId = bookingRequest.getShowtimeId();
        int totalPrice = bookingRequest.getTotalPrice();
        System.out.println(userId+" "+ id+" "+ movieId+" "+ showtimeId);
        BookingEntity booking = bookingService.bookSeats(userId, id, movieId, showtimeId, totalPrice);
        return ResponseEntity.ok(booking);
    }
}
