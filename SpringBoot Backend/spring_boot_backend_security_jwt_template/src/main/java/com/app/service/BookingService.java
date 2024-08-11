package com.app.service;

import java.util.List;

import com.app.entities.Booking;

public interface BookingService {

	public Booking bookSeats(Long userId, List<Integer> seatNos, Long movieId, Long showtimeId);
}
