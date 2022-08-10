package demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.Game;
import demo.entity.IssueUser;
import demo.entity.User;
import demo.response.CreateGameResponse;
import demo.response.SocketAllResponse;
import demo.service.GameService;
import demo.service.IssueUserService;

@RestController
public class GameController {
	
	@Autowired
	GameService gameService;

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	IssueUserService issueUserService;
	
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
		sendMessageToClients(rp.getGameId(), rp.getUserId());
		return rp;
	}
	
	public void sendMessageToClients(int gameId, int userId)
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
