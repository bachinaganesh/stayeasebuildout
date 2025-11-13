package com.stayease.stayeasebuildout.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stayease.stayeasebuildout.dtos.HotelRequestDto;
import com.stayease.stayeasebuildout.dtos.HotelResponseDto;
import com.stayease.stayeasebuildout.models.Hotel;
import com.stayease.stayeasebuildout.service.HotelService;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        Hotel createdHotel = this.hotelService.createHotel(hotel);
        return new ResponseEntity<>(createdHotel, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = this.hotelService.getAllHotels();
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PutMapping("/{hotelId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<?> updateHotel(@RequestBody HotelRequestDto hotelRequestDto, @PathVariable Long hotelId) {
        Optional<Hotel> hotel = this.hotelService.getHotelById(hotelId);
        if(hotel.isPresent()) {
            Hotel actualHotel = hotel.get();
            actualHotel.setName(hotelRequestDto.getName());
            actualHotel.setAvailableRooms(hotelRequestDto.getAvailableRooms());
            HotelResponseDto responseDto = this.hotelService.updateHotel(actualHotel);
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{hotelId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteHotel(@PathVariable Long hotelId) {
        Optional<Hotel> hotel = this.hotelService.getHotelById(hotelId);
        HttpStatus status = HttpStatus.NOT_FOUND;
        if(hotel.isPresent()) {
            status = HttpStatus.NO_CONTENT;
        }
        return new ResponseEntity<>(status);
    }
}
