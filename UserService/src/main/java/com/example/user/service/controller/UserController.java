package com.example.user.service.controller;

import com.example.user.service.entity.User;
import com.example.user.service.service.UserService;
import com.example.user.service.service.UserServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryCount = 0;
    //get single user
//    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
//    @Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId){
        logger.info("retrying... retryCount: {}", retryCount);
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating fallback method for above circuit breaker
    public ResponseEntity<User>  ratingHotelFallback(String userId, Exception ex){
        logger.info("Fallback is executed because service is down : ", ex.getMessage());

        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This is a dummy user")
                .userId("123456")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    //get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }


}
