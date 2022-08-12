package demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Game;
import demo.entity.IssueUser;
import demo.entity.User;
import demo.response.CreateGameResponse;
import demo.response.ResponseEntity;
import demo.response.SocketAllResponse;
import demo.service.GameService;
import demo.service.IssueUserService;
import demo.service.UserService;

@RestController
@CrossOrigin
public class GameController {
	
	@Autowired
	GameService gameService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	IssueUserService issueUserService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/createGame/{userName}")
	public CreateGameResponse createGame(@PathVariable String userName)
	{
		CreateGameResponse rp = gameService.createGame(userName);
		//sendMessageToClients(rp.getGameId(), rp.getUserId());
		return rp;
	}
	
	
	@GetMapping("/joinGame/{gameId}/{userName}")
	public CreateGameResponse joinGame(@PathVariable int gameId, @PathVariable String userName)
	{
		var rp = gameService.joinGame(gameId, userName);
		sendMessageToClients(rp.getGameId());
		return rp;
	}
	
	@GetMapping("callData/{gameId}")
	public void callData(@PathVariable int gameId)
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
		
		sar.setIssuePoints(allIssuePoints);
		ResponseEntity re = new ResponseEntity();
		re.setGameId(gameId);
		re.setState("CREATED");
		
		simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameId, sar);
		//sendMessageToClients(gameId);
	}
	
	public void sendMessageToClients(int gameId)
	{
		Game game = gameService.getGame(gameId);
		
		//List<User> x = new ArrayList<>(game.getUsers());
		SocketAllResponse sar = sarFunc(gameId);
		simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameId, "123");
	}
	
	SocketAllResponse sarFunc(int gameId)
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
