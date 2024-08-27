package com.example.rating.RatingService.controller;

import com.example.rating.RatingService.entity.Rating;
import com.example.rating.RatingService.service.RatingService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    //create ratings
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        Rating rating1 = ratingService.createRating(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<Rating>> getRatings(){
        List<Rating> allRatings = ratingService.getAllRatings();
        return ResponseEntity.ok(allRatings);
    }

    //get all
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId){
        List<Rating> allRatings = ratingService.getAllRatingsByUserId(userId);
        return ResponseEntity.ok(allRatings);
    }

    //get all
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId){
        List<Rating> allRatings = ratingService.getAllRatingsByHotelId(hotelId);
        return ResponseEntity.ok(allRatings);
    }
}
