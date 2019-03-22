package com.witchok.bootnet.domain.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.witchok.bootnet.domain.post.Post;
import com.witchok.bootnet.domain.users.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comment")

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@JsonIgnoreProperties("id")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment_text")
    private String text;

    @Column(name = "created_date")
    private Date createdAt;

    @Column(name = "updated_date")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_commentator")
    private User commentCreator;

    @ManyToOne
    @JoinColumn(name = "fk_post")
    private Post post;

    @PreUpdate
    private void updateDate(){
        this.updatedAt = new Date();
    }

    @PrePersist
    private void setCreatedDate(){
        this.createdAt = new Date();
    }


}
