package com.tushar.securitycourse.repositories;

import com.tushar.securitycourse.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
