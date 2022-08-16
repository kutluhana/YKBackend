package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.User;
import demo.service.GameService;
import demo.service.UserService;




@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	GameService gameService;
	
	@PostMapping("/addUser")
	public  User addUser(@RequestBody User user)
	{
		return userService.saveUser(user);
	}
	
	@PostMapping("/addUsers")
	public  List<User> addUsers(@RequestBody List<User> users)
	{
		return userService.saveUsers(users);
	}
	
	@GetMapping("/users")
	public List<User> findAllUsers()
	{
		return userService.getUsers();
	}
	
	@GetMapping("/user/{id}")
	public User findIssueById(@PathVariable int id)
	{
		return userService.findUserById(id);
	}
	
	@PutMapping("updateUser")
	public User updateIssue(@RequestBody User user)
	{
		return userService.updateUser(user);
	}
	
	@PutMapping("/changeUserName/{gameId}/{userId}/{userName}")
	public User updateUserName(@PathVariable int gameId, @PathVariable int userId, @PathVariable String userName)
	{
		User retUser = userService.updateUserName(userId, userName);
		
		gameService.sendRequests(gameId);
		
		return retUser;
	}
	
	@DeleteMapping("/deleteUser/{id}")
	public String deleteIssue(@PathVariable int id)
	{
		return userService.deleteUser(id);
	}
	
}

