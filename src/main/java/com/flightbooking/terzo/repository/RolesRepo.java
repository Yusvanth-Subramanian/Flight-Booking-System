package com.flightbooking.terzo.repository;

import com.flightbooking.terzo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Role,Long> {

    Role findByName(String name);

}
