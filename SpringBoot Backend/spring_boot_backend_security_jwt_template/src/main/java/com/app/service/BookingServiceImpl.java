package com.app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.BookingEntity;
import com.app.entities.BookingSeats;
import com.app.entities.SeatEntity;
import com.app.entities.ShowtimesEntity;
import com.app.entities.UserEntity;
import com.app.repository.BookingRepository;
import com.app.repository.BookingSeatsRepository;
import com.app.repository.SeatRepository;
import com.app.repository.ShowtimesRepository;
import com.app.repository.UserEntityRepository;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private BookingSeatsRepository bookingSeatsRepository;
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowtimesRepository showtimesRepository;

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public BookingEntity bookSeats(Long userId, List<Long> id, Long movieId, Long showtimeId, int totalPrice) {
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
        List<SeatEntity> seats = seatRepository.findByIdInAndShowtime(id, showtime);
        if (seats.size() != id.size()) {
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
        booking.setShowtime(showtime);
        booking.setBookingDate(LocalDateTime.now()); // Set the booking date to the current time
        booking.setTotalPrice(totalPrice);
        bookingRepository.save(booking);

        return booking;
    }
    @Transactional
    public List<BookingEntity> getBookingsByUserId(Long userId) {
    	List<BookingEntity> bookings = bookingRepository.findByUserId(userId);
        // Initialize lazy-loaded entities
        bookings.forEach(booking -> {
            booking.getUser().getFirstName();  // Force initialization
            booking.getShowtime().getMovie().getMName();  // Force initialization
        });
        return bookings;
    }
    @Transactional
    public List<BookingSeats> getSeatsByBookingId(Long bookingId) {
        return bookingSeatsRepository.findByBookingId(bookingId);
    }
}
