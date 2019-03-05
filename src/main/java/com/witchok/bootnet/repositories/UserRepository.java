package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>,
        PagingAndSortingRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);


}
