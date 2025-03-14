package com.madhav.blog.app.repository;

import com.madhav.blog.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
