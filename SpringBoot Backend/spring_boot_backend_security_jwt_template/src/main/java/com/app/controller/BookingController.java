package com.app.controller;

import com.app.entities.Booking;
import com.app.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> bookSeats(
            @RequestParam Long userId,
            @RequestParam List<Integer> seatNos,
            @RequestParam Long movieId,
            @RequestParam Long showtimeId) {
        Booking booking = bookingService.bookSeats(userId, seatNos, movieId, showtimeId);
        return ResponseEntity.ok(booking);
    }
}
