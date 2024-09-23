package com.ExpressLane.Controller;

import com.ExpressLane.Model.Role;
import com.ExpressLane.Model.RoleName;
import com.ExpressLane.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleIntializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleIntializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (RoleName roleName : RoleName.values()) {
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Role " + roleName + " created");
            }
        }
    }
}
