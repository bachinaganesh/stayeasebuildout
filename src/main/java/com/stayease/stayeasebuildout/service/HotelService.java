package com.stayease.stayeasebuildout.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stayease.stayeasebuildout.dtos.HotelResponseDto;
import com.stayease.stayeasebuildout.models.Hotel;
import com.stayease.stayeasebuildout.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel(@RequestBody Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }

    public List<Hotel> getAllHotels() {
        return this.hotelRepository.findAll();
    }

    public Optional<Hotel> getHotelById(Long hotelId) {
        return this.hotelRepository.findById(hotelId);
    }

    public HotelResponseDto updateHotel(Hotel hotel) {
        Hotel updatedHotel = this.hotelRepository.save(hotel);
        HotelResponseDto responseDto = new HotelResponseDto();
        responseDto.setName(updatedHotel.getName());
        responseDto.setAvailableRooms(updatedHotel.getAvailableRooms());
        responseDto.setId(updatedHotel.getId());
        return responseDto;
    }
    
    public void deleteHotel(Long hotelId) {
        this.hotelRepository.deleteById(hotelId);
    }
}
