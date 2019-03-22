package com.witchok.bootnet.repositories;

import com.witchok.bootnet.domain.userRole.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends CrudRepository<Role, Integer>,
        PagingAndSortingRepository<Role, Integer> {
}
