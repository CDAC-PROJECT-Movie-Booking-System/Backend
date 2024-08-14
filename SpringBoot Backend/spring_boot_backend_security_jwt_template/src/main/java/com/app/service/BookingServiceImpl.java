package com.app.service;

import com.app.entities.BookingEntity;
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;
import com.app.entities.UserEntity;
import com.app.repository.BookingRepository;
import com.app.repository.SeatRepository;
import com.app.repository.ShowtimesRepository;
import com.app.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimesRepository showtimesRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public BookingEntity bookSeats(Long userId, List<Integer> seatNos, Long movieId, Long showtimeId) {
        // Fetch the user
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        UserEntity user = userOpt.get();

        // Fetch the showtime and its seats
        Optional<ShowtimesEntity> showtimeOpt = showtimesRepository.findById(showtimeId);
        if (!showtimeOpt.isPresent() || !showtimeOpt.get().getMovie().getId().equals(movieId)) {
            throw new RuntimeException("Showtime or movie not found");
        }
        ShowtimesEntity showtime = showtimeOpt.get();

        // Fetch and validate seats
        List<SeatEntity> seats = seatRepository.findBySeatNoInAndShowtime(seatNos, showtime);
        if (seats.size() != seatNos.size()) {
            throw new RuntimeException("Some seats not found");
        }

        // Check availability
        for (SeatEntity seat : seats) {
            if (!seat.getIsSeatAvailable()) {
                throw new RuntimeException("One or more seats are already booked");
            }
            seat.setIsSeatAvailable(false);
        }
        seatRepository.saveAll(seats);

        // Create booking
        BookingEntity booking = new BookingEntity();
        booking.setUser(user);
        bookingRepository.save(booking);

        return booking;
    }
}
