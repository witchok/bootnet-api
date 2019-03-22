package com.witchok.bootnet.domain.post;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.witchok.bootnet.domain.comment.Comment;
import com.witchok.bootnet.domain.users.User;
import lombok.*;
import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "post")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties("id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "post_text")
    private String text;

    @Column(name = "created_date")
    private Date createdAt;

    @Column(name = "updated_date")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="user_creator")
//    @OnDelete(action = OnD)
    private User postCreator;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @PreUpdate
    private void updateDate(){
        this.updatedAt = new Date();
    }

    @PrePersist
    private void setCreatedDate(){
        this.createdAt = new Date();
    }
}