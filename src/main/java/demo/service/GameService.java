package demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import demo.entity.Game;
import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.entity.User;
import demo.repo.GameRepo;
import demo.repo.IssueRepo;
import demo.response.CreateGameResponse;
import demo.response.SocketAllResponse;

@Service
public class GameService {
	
	@Autowired
	private GameRepo gameRepo;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	private IssueUserService issueUserService;
	
	@Autowired
	private IssueRepo issueRepo;
	
	public CreateGameResponse createGame(String userName, String gameName)
	{
		Game game = new Game();
		game.setGameStatus("CREATED");
		game.setGameName(gameName);
		
		gameRepo.save(game);
		User user = new User();
		user.setName(userName);
		user.setInGame(game);
		userService.saveUser(user);
		var cgr = new CreateGameResponse();
		cgr.setGameId(game.getId());
		cgr.setUserId(user.getId());
		return cgr;
	}
	
	public CreateGameResponse joinGame(int gameId, String userName)
	{
		Game game = getGame(gameId);
		User user = new User();
		user.setName(userName);
		user.setInGame(game);
		
		userService.saveUser(user);
		
		var cgr = new CreateGameResponse();
		cgr.setGameId(game.getId());
		cgr.setUserId(user.getId());
		return cgr;
	}
	
	public Game getGame(int gameId)
	{
		return gameRepo.getById(gameId);
	}
	
	public void sendRequests(int gameId)
	{
		var sar = new SocketAllResponse();
		sar.setGame(getGame(gameId));
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
		
		simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameId, sar);
	}
	
	public void revealCards(int gameId)
	{
		Game game = getGame(gameId);
		Issue selectedIssue = game.getSelectedIssue();
		
		
		List<IssueUser> allIssuePoints = issueUserService.getIssueUsers();
		double res = 0;
		int count = 0;
		for(IssueUser check : allIssuePoints)
		{
			if(check.getIssue().getId() == selectedIssue.getId())
			{
				res += check.getPoint();
				count++;
			}
		}
		
		selectedIssue.setStoryPoint(res / count);
		selectedIssue.setIsRevealed(true);
		issueRepo.save(selectedIssue);
		
		game.setGameStatus("REVEALING");
		gameRepo.save(game);
		
	}
	
	public void sendRequestByIssue(int gameId, int issueId)
	{
		var sar = new SocketAllResponse();
		sar.setGame(getGame(gameId));
		sar.setUsers(new ArrayList<>(sar.getGame().getUsers()));
		sar.setIssues(new ArrayList<>(sar.getGame().getIssuesInGame()));
		List<IssueUser> issueUsers = new ArrayList<>();
		
		List<IssueUser> allIssuePoints = issueUserService.getIssueUsers();
		
		for(IssueUser check : allIssuePoints)
		{
			if(check.getIssue().getId() == issueId)
				issueUsers.add(check);
		}
		
		sar.setIssuePoints(allIssuePoints);
		
		simpMessagingTemplate.convertAndSend("/topic/game-progress/" + gameId, sar);
	}
	
	
}
