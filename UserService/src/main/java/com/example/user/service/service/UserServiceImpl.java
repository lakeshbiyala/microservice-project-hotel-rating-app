package com.example.user.service.service;

import com.example.user.service.entity.Hotel;
import com.example.user.service.entity.Rating;
import com.example.user.service.entity.User;
import com.example.user.service.exceptions.ResourceNotFoundException;
import com.example.user.service.externalService.HotelService;
import com.example.user.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {

        //generate unique uid
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from databse with help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
//
        //get ratings of above user from rating service
//        http://localhost:8083/ratings/users/185edb57-3032-4465-a0b2-40c7bbfa2e21
        Rating[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}", ratingsOfUser);

        List<Rating> ratings = Arrays.asList(ratingsOfUser);
        List<Rating> ratingList = ratings.stream().map( rating -> {

            //api call to hotel service to get the hotel
            //http://localhost:8082/hotels/0e329d6a-372f-4d40-8529-5d09b459bef6

            //getting hotel using rest template
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code: {}", forEntity.getStatusCode());


            //getting hotel using feign client
            Hotel hotel = hotelService.getHotel(rating.getHotelId());

            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());


        user.setRatings(ratingList);
        return user;
    }
}
