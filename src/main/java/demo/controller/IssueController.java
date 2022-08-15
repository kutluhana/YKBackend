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
import demo.request.IssueRequest;
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
	public  Issue addIssue(@RequestBody IssueRequest issueRequest, @PathVariable int userId, @PathVariable int gameId)
	{
		Issue newIssue = new Issue();
		Game game = gameService.getGame(gameId);
		
		newIssue.setDescription(issueRequest.getDescription());
		newIssue.setIsRevealed(false);
		newIssue.setIssueName(issueRequest.getIssueName());
		newIssue.setOwnerGame(game);
		newIssue.setStoryPoint(0.0);
		
		Issue sendedIssue = issueService.saveIssue(newIssue);
		
		gameService.sendRequests(gameId);
		
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
	
	@GetMapping("/selectIssue/{gameId}/{issueId}")
	public void selectIssue(@PathVariable int gameId, @PathVariable int issueId)
	{
		issueService.selectIssue(gameId, issueId);
		
		gameService.sendRequests(gameId);
	}
	
	@GetMapping("/appendPointIssue/{gameId}/{issueId}/{point}")
	public Issue appendPointIssue(@PathVariable int gameId, @PathVariable int issueId, @PathVariable int point)
	{
		Issue is = issueService.appendPoint(issueId, point);
		
		gameService.sendRequests(gameId);
		
		return is;
	}
}
