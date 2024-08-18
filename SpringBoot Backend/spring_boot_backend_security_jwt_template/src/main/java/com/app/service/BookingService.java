package com.app.service;

import java.util.List;

import com.app.entities.BookingEntity;

public interface BookingService {

	public BookingEntity bookSeats(Long userId, List<Long> id, Long movieId, Long showtimeId, int totalPrice);
}
