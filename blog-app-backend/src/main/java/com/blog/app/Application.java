package com.blog.app;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.app.config.AppConstants;
import com.blog.app.entities.Role;
import com.blog.app.repos.RoleRepo;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
public class Application implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo rRepo;
	
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("2905"));
		
		try {
			Role role1 = new Role();
			
			role1.setId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			
			role2.setId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = new ArrayList<>();
			roles.add(role1);
			roles.add(role2);
			
			/*List<Role> savedRoles =*/  this.rRepo.saveAll(roles);
			
//			savedRoles.forEach(r -> System.out.println(r.getName()));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
