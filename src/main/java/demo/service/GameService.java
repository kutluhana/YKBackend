package demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.Game;
import demo.entity.User;
import demo.repo.GameRepo;
import demo.response.CreateGameResponse;

@Service
public class GameService {
	
	@Autowired
	private GameRepo gameRepo;
	
	@Autowired
	private UserService userService;
	
	public CreateGameResponse createGame(String userName)
	{
		Game game = new Game();
		game.setGameStatus("CREATED");
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
	
	
}
