package Util;

import Entity.Role;
import Entity.User;
import Repository.RoleRepository;
import Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class DataLoader implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  public void run(String... args) throws Exception {
    Role adminRole = new Role();
    adminRole.setName("ROLE_ADMIN");
    roleRepository.save(adminRole);

    Role userRole = new Role();
    userRole.setName("ROLE_USER");
    roleRepository.save(userRole);

    User admin = new User();
    admin.setUsername("admin");
    admin.setPassword(new BCryptPasswordEncoder().encode("password"));
    admin.setRoles(new HashSet<>(Arrays.asList(adminRole)));
    userRepository.save(admin);

    User user = new User();
    user.setUsername("user");
    user.setPassword(new BCryptPasswordEncoder().encode("password"));
    user.setRoles(new HashSet<>(Arrays.asList(userRole)));
    userRepository.save(user);
  }
}

