package com.witchok.bootnet.domain.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"subscribers","subscriptions","id"})
@Builder
@Entity
@Table(name = "bootuser")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="bootuser_subscriber",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "subscriber_id")} )
    private Set<User> subscribers;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="bootuser_subscriber",
            joinColumns = {@JoinColumn(name = "subscriber_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")} )
    private Set<User> subscriptions;

    public User(String username, String name, String lastName, String email, String password, String profileImage){
        this(null, username, name, lastName, email, password, profileImage,
                new Date(), new LinkedHashSet(), new LinkedHashSet<>());
    }

//    @PrePersist
//    void createdAt(){
//        this.createdAt = new Date();
//    }
}

