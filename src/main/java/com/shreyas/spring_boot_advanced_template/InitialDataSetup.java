package com.shreyas.spring_boot_advanced_template;

import com.shreyas.spring_boot_advanced_template.entity.Constants.RoleType;
import com.shreyas.spring_boot_advanced_template.entity.Role;
import com.shreyas.spring_boot_advanced_template.entity.User;
import com.shreyas.spring_boot_advanced_template.repository.interfaces.IRoleRepo;
import com.shreyas.spring_boot_advanced_template.repository.interfaces.IUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Component
public class InitialDataSetup implements CommandLineRunner {
    private final IRoleRepo roleRepo;
    private final IUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AppConstant appConstant;

    public InitialDataSetup(IRoleRepo roleRepo, IUserRepo userRepository, PasswordEncoder passwordEncoder, AppConstant appCon) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appConstant = appCon;
    }

    @Override
    public void run(String... args) throws Exception {
        if(!appConstant.RunStartupFile())
            return;
        createRoleIfNotFound(RoleType.ROLE_ADMIN);
        createRoleIfNotFound(RoleType.ROLE_STUDENT);
        createRoleIfNotFound(RoleType.ROLE_TEACHER);
        createRoleIfNotFound(RoleType.ROLE_USER);
        createAdminUserIfNotFound();
    }

    private void createRoleIfNotFound(RoleType roleName) {
        Optional<Role> role = roleRepo.findByRoleName(roleName);
        if (role.isEmpty()) {
            Role newRole = new Role();
            newRole.setId(UUID.randomUUID());
            newRole.setRoleName(roleName);
            newRole.setIsEnabled(true);
            roleRepo.save(newRole);
        }
    }

    private void createAdminUserIfNotFound(){
        if (userRepo.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("User");
            adminUser.setEmail("spkothari0@gmail.com");
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin")); // Change this to a secure password
            adminUser.setIsLocked(false);
            adminUser.setIsEnabled(true);
            adminUser.setDob(LocalDate.of(1998, 3, 5));
            Optional<Role> adminRole = roleRepo.findByRoleName(RoleType.ROLE_ADMIN);
            adminRole.ifPresent(adminUser::setRole);
            userRepo.save(adminUser);
            System.out.println("Admin user created successfully!");
        }
        else {
            System.out.println("Admin user already exists!");
        }
    }

}
