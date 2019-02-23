package com.witchok.bootnet.domain.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bootuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;
    private String name;

    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    @Column(name="profile_img")
    private String profileImage;
    @Column(name="registration_date")
    private Date createdAt;

    public User(String username, String name, String lastName, String email, String password, String profileImage){
        this(null, username, name, lastName, email, password, profileImage, new Date());
    }

//    @PrePersist
//    void createdAt(){
//        this.createdAt = new Date();
//    }
}

