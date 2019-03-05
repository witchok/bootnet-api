package com.witchok.bootnet.domain.users;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"subscribers","subscriptions"})
@Builder
@Entity
@Table(name = "bootuser")
@JsonIgnoreProperties(value = {"id","password"}, allowSetters = true)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @JsonSerialize(using = CustomSetUserSerializer.class)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="bootuser_subscriber",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "subscriber_id")} )
    private Set<User> subscribers = new HashSet<>();

//    @JsonIgnore
//    @JsonSerialize(using = CustomSetUserSerializer.class)
    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.LAZY)
    private Set<User> subscriptions = new HashSet<>();

    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}

