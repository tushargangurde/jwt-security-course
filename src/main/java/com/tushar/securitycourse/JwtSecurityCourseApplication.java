package com.tushar.securitycourse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtSecurityCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtSecurityCourseApplication.class, args);
    }

   /* @Bean
    CommandLineRunner runner(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new AppUser(null, "Tushar Gangurde", "tushar", "password", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Bobby Gangurde", "bobby", "password", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Jitendra More", "jitendra", "password", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Rahul", "rahul", "password", new ArrayList<>()));

            userService.addRoleToUser("tushar", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("bobby", "ROLE_MANAGER");
            userService.addRoleToUser("jitendra", "ROLE_ADMIN");
            userService.addRoleToUser("rahul", "ROLE_USER");
            userService.addRoleToUser("tushar", "ROLE_ADMIN");
            userService.addRoleToUser("tushar", "ROLE_USER");
        };
    }*/

}
