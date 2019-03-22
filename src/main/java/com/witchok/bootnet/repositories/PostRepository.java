package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.post.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface PostRepository extends CrudRepository<Post, Integer>,
        PagingAndSortingRepository<Post, Integer> {
}
