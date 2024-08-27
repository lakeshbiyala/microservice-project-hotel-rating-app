package com.example.hotel.service;

import com.example.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel create(Hotel hotel);

    //get hotel
    Hotel getHotel(String id);

    //get all hotels
    List<Hotel> getALlHotels();

}
