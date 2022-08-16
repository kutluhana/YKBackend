package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.User;
import demo.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	public User saveUser(User user)
	{
		return userRepo.save(user);
	}
	
	public List<User> saveUsers(List<User> users)
	{
		return userRepo.saveAll(users);
	}
	
	public List<User> getUsers()
	{
		return userRepo.findAll();
	}
	
	public User findUserById(int id)
	{
		return userRepo.findById(id).orElse(null);
	}
	
	public User getUserByName(String name)
	{
		return userRepo.findByName(name);
	}
	
	public String deleteUser(int id)
	{
		userRepo.deleteById(id);	
		return "User deleted + " + id;
	}
	
	public User updateUser(User user)
	{
		User existingUser = userRepo.findById(user.getId()).orElse(null);
		existingUser.setName(user.getName());
		return userRepo.save(existingUser);
	}
	
	public User updateUserName(int userId, String newUserName)
	{
		User user = userRepo.findById(userId).orElse(null);
		
		user.setName(newUserName);
		
		return userRepo.save(user);
	}
}
