package com.springboot.erp.users.repositories;

import com.springboot.erp.users.entitys.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    
}
