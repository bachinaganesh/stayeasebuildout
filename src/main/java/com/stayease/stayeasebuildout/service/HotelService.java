package com.stayease.stayeasebuildout.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.stayease.stayeasebuildout.models.Hotel;
import com.stayease.stayeasebuildout.repository.HotelRepository;

@Service
public class HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel(@RequestBody Hotel hotel) {
        return this.hotelRepository.save(hotel);
    }
}
