package com.example.user.service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "micro_users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @Column(name = "id")
    private String userId;

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "about")
    private String about;

    @Transient
    private List<Rating> ratings = new ArrayList<>();

}
