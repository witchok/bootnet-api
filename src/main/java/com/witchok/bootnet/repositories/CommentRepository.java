package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.comment.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer>,
        PagingAndSortingRepository<Comment, Integer> {
}
