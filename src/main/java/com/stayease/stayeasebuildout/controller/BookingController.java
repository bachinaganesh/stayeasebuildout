package com.stayease.stayeasebuildout.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stayease.stayeasebuildout.dtos.Booking;
import com.stayease.stayeasebuildout.dtos.BookingRequestDto;
import com.stayease.stayeasebuildout.models.Hotel;
import com.stayease.stayeasebuildout.service.BookingService;
import com.stayease.stayeasebuildout.service.HotelService;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private HotelService hotelService;

    @PostMapping("/{hotelId}")
    @PreAuthorize("hasRole('Customer')")
    public ResponseEntity<?> bookHotel(@PathVariable Long hotelId, @RequestBody BookingRequestDto bookingRequestDto) {
        Optional<Hotel> hotel = this.hotelService.getHotelById(hotelId);
        if (hotel.isPresent()) {
            Hotel actuaHotel = hotel.get();
            if (actuaHotel.getAvailableRooms() <= 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No rooms available");
            } else {
                Booking booking = new Booking();
                booking.setHotelId(actuaHotel.getId());
                booking.setCheckInDate(bookingRequestDto.getCheckInDate());
                booking.setCheckOutDate(bookingRequestDto.getCheckOutDate());
                actuaHotel.setAvailableRooms(actuaHotel.getAvailableRooms() - 1);
                this.hotelService.createHotel(actuaHotel);
                Booking actualBooked = this.bookingService.bookHotel(booking);
                return new ResponseEntity<>(actualBooked, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
        Optional<Booking> booking = this.bookingService.getBooking(bookingId);
        if(!booking.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(booking.get(), HttpStatus.OK);
    }


    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        Optional<Booking> booking = this.bookingService.getBooking(bookingId);
        if(booking.isPresent()) {
            this.bookingService.deleteBooking(bookingId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
