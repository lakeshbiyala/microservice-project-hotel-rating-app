package com.example.rating.RatingService.service;

import com.example.rating.RatingService.entity.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating createRating(Rating rating);

    //get all ratings
    List<Rating> getAllRatings();

    //get all ratings by UserId
    List<Rating> getAllRatingsByUserId(String userId);

    //get all ratings by HotelId
    List<Rating> getAllRatingsByHotelId(String hotelId);
}
