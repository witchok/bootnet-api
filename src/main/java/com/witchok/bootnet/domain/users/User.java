package com.witchok.bootnet.domain.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
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

    private Date createdAt;

    public User(String username, String name, String lastName, String email, String password){
        this(null, username, name, lastName, email, password, new Date());
    }

//    @PrePersist
//    void createdAt(){
//        this.createdAt = new Date();
//    }
}

