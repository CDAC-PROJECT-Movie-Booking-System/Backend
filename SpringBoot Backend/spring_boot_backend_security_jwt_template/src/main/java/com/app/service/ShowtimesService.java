package com.app.service;

import java.util.List;

import com.app.dto.SeatDTO;
import com.app.entities.SeatEntity;

public interface ShowtimesService {

	public List<SeatDTO> getSeatsForShowtime(Long movieId, Long showtimeId);
}
