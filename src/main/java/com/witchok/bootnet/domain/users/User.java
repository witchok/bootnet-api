package com.witchok.bootnet.domain.users;

import com.fasterxml.jackson.annotation.*;
import com.witchok.bootnet.domain.comment.Comment;
import com.witchok.bootnet.domain.post.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

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
//    @CreatedDate
    private Date createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="bootuser_subscriber",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "subscriber_id")} )
    private Set<User> subscribers = new HashSet<>();

    @ManyToMany(mappedBy = "subscribers", fetch = FetchType.LAZY)
    private Set<User> subscriptions = new HashSet<>();

    @OneToMany(mappedBy = "commentCreator")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "postCreator")
    private Set<Post> posts;


    @PrePersist
    void createdAt(){
        this.createdAt = new Date();
    }
}

