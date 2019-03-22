package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.post.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer>,
        PagingAndSortingRepository<Post, Integer> {
}
