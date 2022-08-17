package demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import demo.response.UserVoteInfo;

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
		Collections.sort(sar.getIssues(), new Comparator<Issue>() {
			@Override
			public int compare(Issue o1, Issue o2) {
				return (o1.getId() < (o2.getId())) ? -1 : (o1.getId() > o2.getId()) ? 1 : 0;
			}
		});
		List<IssueUser> issueUsers = new ArrayList<>();
		
		List<IssueUser> allIssuePoints = issueUserService.getIssueUsers();
		Set<Integer> setUser = new HashSet<>();
		for(IssueUser check : allIssuePoints)
		{
			if(sar.getGame().getSelectedIssue() != null  &&  check.getUser().getInGame().getId() == gameId && check.getIssue().getId() == sar.getGame().getSelectedIssue().getId())
			{
				issueUsers.add(check);
				setUser.add(check.getUser().getId());
			}
		}
		
		sar.setIssuePoints(issueUsers);
		
		if(sar.getGame().getGameStatus().equals("VOTING"))
		{
			List<UserVoteInfo> userVotes = new ArrayList<>();
			
			List<User> inGameUsers = sar.getUsers();
			
			for(User user: inGameUsers)
			{
				UserVoteInfo userVote = new UserVoteInfo();
				userVote.setUserId(user.getId());
				userVote.setUserName(user.getName());
				if(setUser.contains(user.getId()))
					userVote.setVote(true);
				else
					userVote.setVote(false);
				userVotes.add(userVote);
			}
			
			sar.setUserVotes(userVotes);
		}
		
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
		if(count != 0)
			selectedIssue.setStoryPoint(res / count);
		else
			selectedIssue.setStoryPoint(0.0);
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
	
	public Game changeGameName(int gameId, String newGameName)
	{
		Game game = gameRepo.findById(gameId).orElse(null);
		
		if(game == null)
			return null;
		
		game.setGameName(newGameName);
		return gameRepo.save(game);
	}
	
}
