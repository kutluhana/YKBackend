package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	private UserRepo userRepo;
	
	@GetMapping("/users") 
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	
	@PostMapping("/save")
	public String saveUser(@RequestBody User user) {
		userRepo.save(user);
		return "Saved...";
	}
}
