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
	
	@PostMapping("/createGame/{userName}/{gameName}")
	public CreateGameResponse createGame(@PathVariable String userName, @PathVariable String gameName)
	{
		CreateGameResponse rp = gameService.createGame(userName, gameName);
		return rp;
	}
	
	
	@GetMapping("/joinGame/{gameId}/{userName}")
	public CreateGameResponse joinGame(@PathVariable int gameId, @PathVariable String userName)
	{
		var rp = gameService.joinGame(gameId, userName);
	    gameService.sendRequests(gameId);
		return rp;
	}
	
	@GetMapping("/callData/{gameId}")
	public void callData(@PathVariable int gameId)
	{
		gameService.sendRequests(gameId);
	}
	
	@GetMapping("/revealCards/{gameId}")
	public void revealCards(@PathVariable int gameId)
	{
		gameService.revealCards(gameId);
		
		gameService.sendRequests(gameId);
	}
}
