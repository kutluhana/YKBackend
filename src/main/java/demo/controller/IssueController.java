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
import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.entity.User;
import demo.response.SocketAllResponse;
import demo.service.GameService;
import demo.service.IssueService;
import demo.service.IssueUserService;


@RestController
@CrossOrigin
public class IssueController {
	
	@Autowired
	IssueService issueService;
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	IssueUserService issueUserService;
	
	@PostMapping("/addIssue/{gameId}/{userId}")
	public  Issue addIssue(@RequestBody Issue issue, @PathVariable int userId, @PathVariable int gameId)
	{
		Issue sendedIssue = issueService.saveIssue(issue);
		
		sendMessageToClients(gameId, userId);
		
		return sendedIssue;
	}
	
	@PostMapping("/addIssues")
	public  List<Issue> addIssues(@RequestBody List<Issue> issues)
	{
		return issueService.saveIssues(issues);
	}
	
	@GetMapping("/issues")
	public List<Issue> findAllIssues()
	{
		return issueService.getIssues();
	}
	
	
	@GetMapping("/issue/{id}")
	public Issue findIssueById(@PathVariable int id)
	{
		return issueService.findIssueById(id);
	}
	
	@PutMapping("updateIssue")
	public Issue updateIssue(@RequestBody Issue issue)
	{
		return issueService.updateIssue(issue);
	}
	
	@DeleteMapping("/deleteIssue/{id}")
	public String deleteIssue(@PathVariable int id)
	{
		return issueService.deleteIssue(id);
	}
	
	public void sendMessageToClients( int gameId, int userId)
	{
		Game game = gameService.getGame(gameId);
		
		List<User> x = new ArrayList<>(game.getUsers());
		SocketAllResponse sar = sarFunc(gameId, userId);
		for(User send : x)
			simpMessagingTemplate.convertAndSend("/topic/" + gameId +"/" +  send.getId(), sar);
	}
	
	SocketAllResponse sarFunc(int gameId, int userId)
	{
		var sar = new SocketAllResponse();
		sar.setGame(gameService.getGame(gameId));
		sar.setUsers(new ArrayList<>(sar.getGame().getUsers()));
		sar.setIssues(new ArrayList<>(sar.getGame().getIssuesInGame()));
		
		List<IssueUser> issueUsers = new ArrayList<>();
		
		List<IssueUser> allIssuePoints = issueUserService.getIssueUsers();
		
		for(IssueUser check : allIssuePoints)
		{
			if(check.getUser().getInGame().getId() == gameId)
				issueUsers.add(check);
		}
		
		sar.setIssuePoints(issueUsers);
		return sar;
	}
	
}
