package com.stayease.stayeasebuildout.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stayease.stayeasebuildout.dtos.Booking;
import com.stayease.stayeasebuildout.repository.BookingRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookHotel(Booking booking) {
        return this.bookingRepository.save(booking);
    }

    public Optional<Booking> getBooking(Long bookingId) {
        return this.bookingRepository.findById(bookingId);
    }

    public void deleteBooking(Long bookingId) {
        this.bookingRepository.deleteById(bookingId);
    }
}
