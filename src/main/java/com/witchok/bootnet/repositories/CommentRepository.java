package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.comment.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface CommentRepository extends CrudRepository<Comment, Integer>,
        PagingAndSortingRepository<Comment, Integer> {
}
