package demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.IssueUser;
import demo.response.IssueUserResponse;
import demo.service.IssueUserService;


@RestController
@CrossOrigin
public class IssueUserController {

	@Autowired
	IssueUserService issueUserService;
	
	@PostMapping("/addPoint/{issueId}/{userId}/{point}")
	public  IssueUser addIssue(@PathVariable int issueId, @PathVariable int userId, @PathVariable int point)
	{
		return issueUserService.saveIssueUser(issueId, userId, point);
	}

	
	@GetMapping("/getAllPoints/{issueId}")
	public List<IssueUserResponse> getAllPoints(@PathVariable int issueId)
	{
		return issueUserService.getAllCards(issueId);
	}
	
	@PostMapping("/revealCards/{issueId}")
	public void revealCards(@PathVariable int issueId)
	{
		issueUserService.revealCards(issueId);
	}
}
