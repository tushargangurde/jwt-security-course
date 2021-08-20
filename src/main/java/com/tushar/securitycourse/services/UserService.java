package com.tushar.securitycourse.services;

import com.tushar.securitycourse.model.AppUser;
import com.tushar.securitycourse.model.Role;

import java.util.List;

public interface UserService {
    AppUser saveUser(AppUser user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    AppUser getUser(String username);

    List<AppUser> getUsers();
}
