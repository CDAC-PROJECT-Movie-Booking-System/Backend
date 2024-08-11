package com.app.service;

import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;
import com.app.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private com.app.repository.SeatRepository seatRepository;

    // Get all seats for a specific showtime
    public List<SeatEntity> getSeatsByShowtime(ShowtimesEntity showtime) {
        return seatRepository.findByShowtime(showtime);
    }

    // Get a seat by seatNo and showtime
    public SeatEntity getSeatBySeatNoAndShowtime(int seatNo, ShowtimesEntity showtime) {
        return seatRepository.findBySeatNoAndShowtime(seatNo, showtime);
    }
}