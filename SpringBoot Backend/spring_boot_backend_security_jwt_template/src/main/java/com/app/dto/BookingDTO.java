package com.app.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookingDTO {

	private Long bookingId;
    private String userName;
    private String movieName;
    private List<Integer> seats;
    private LocalTime showtime;
    private LocalDate bookingDate;
    private LocalDate showDate;
    private Integer amount;
}
