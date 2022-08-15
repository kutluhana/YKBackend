package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.Game;
import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.repo.GameRepo;
import demo.repo.IssueRepo;
import demo.repo.IssueUserRepo;

@Service
public class IssueService {

	@Autowired
	IssueRepo issueRepo;
	
	@Autowired
	GameService gameService;
	
	@Autowired
	GameRepo gameRepo;
	
	@Autowired
	IssueUserRepo issueUserRepo;
	
	public Issue saveIssue(Issue issue)
	{
		return issueRepo.save(issue);
	}
	
	public List<Issue> saveIssues(List<Issue> issues)
	{
		return issueRepo.saveAll(issues);
	}
	
	public List<Issue> getIssues()
	{
		return issueRepo.findAll();
	}
	
	public Issue findIssueById(int id)
	{
		return issueRepo.findById(id).orElse(null);
	}
	
	public String deleteIssue(int id)
	{
		issueRepo.deleteById(id);	
		return "Issue deleted + " + id;
	}
	
	public Issue updateIssue(Issue issue)
	{
		Issue existingIssue = issueRepo.findById(issue.getId()).orElse(null);
		existingIssue.setIssueName(issue.getIssueName());
		existingIssue.setDescription(issue.getDescription());
		return issueRepo.save(existingIssue);
	}
	
	public void selectIssue(int gameId, int issueId)
	{
		Issue issueToSelected = findIssueById(issueId);
		
		Game game = gameService.getGame(gameId);
		
		game.setSelectedIssue(issueToSelected);
		game.setGameStatus("VOTING");
		
		List<IssueUser> issueUsers = issueUserRepo.findByIssueId(issueId);
		
		for(IssueUser issueUser : issueUsers)
		{
			issueUserRepo.deleteById(issueUser.getId());
		}
		
		gameRepo.save(game);
	}
	
	public Issue appendPoint(int issueId, int point)
	{
		Issue issue = findIssueById(issueId);
		issue.setStoryPoint((double)point);
		
		return issueRepo.save(issue);
	}
}
