package demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Game;
import demo.entity.IssueUser;
import demo.entity.User;
import demo.repo.UserRepo;
import demo.response.IssueUserResponse;
import demo.response.SocketAllResponse;
import demo.service.GameService;
import demo.service.IssueUserService;
import demo.service.UserService;


@RestController
@CrossOrigin
public class IssueUserController {

	@Autowired
	IssueUserService issueUserService;
	
	@Autowired 
	UserService userService;
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	GameService gameService;
	
	@PostMapping("/addPoint/{gameId}/{issueId}/{userId}/{point}")
	public  IssueUser addIssue(@PathVariable int gameId, @PathVariable int issueId, @PathVariable int userId, @PathVariable int point)
	{
		IssueUser issUser = issueUserService.saveIssueUser(issueId, userId, point);
		
		gameService.sendRequests(gameId);
		
		return issUser;
	}

	
	@GetMapping("/getAllPoints/{issueId}")
	public List<IssueUserResponse> getAllPoints(@PathVariable int issueId)
	{
		return issueUserService.getAllCards(issueId);
	}
	
	/*@PostMapping("/revealCards/{issueId}")
	public void revealCards(@PathVariable int issueId)
	{
		issueUserService.revealCards(issueId);
	}*/
}
