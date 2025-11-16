package com.stayease.stayeasebuildout.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stayease.stayeasebuildout.dtos.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{

}
