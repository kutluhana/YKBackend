package demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.repo.IssueUserRepo;
import demo.response.IssueUserResponse;

@Service
public class IssueUserService {

	@Autowired
	IssueUserRepo issueUserRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	IssueService issueService;
	
	public IssueUser saveIssueUser(int issueId, int userId, int point)
	{
		List<IssueUser> issueUsers = getIssueUsers();
		
		for(IssueUser iss : issueUsers)
		{
			if(iss.getIssue().getId() == issueId && iss.getUser().getId() == userId)
			{
				iss.setPoint(point);
				return issueUserRepo.save(iss);
			}
		}
		
		IssueUser issue = new IssueUser();
		
		issue.setIssue(issueService.findIssueById(issueId));
		issue.setPoint(point);
		issue.setUser(userService.findUserById(userId));
		
		return issueUserRepo.save(issue);
	}
	
	public List<IssueUser> saveIssueUsers(List<IssueUser> issueUsers)
	{
		return issueUserRepo.saveAll(issueUsers);
	}
	
	public List<IssueUser> getIssueUsers()
	{
		return issueUserRepo.findAll();
	}
	
	public IssueUser findIssueUserById(int id)
	{
		return issueUserRepo.findById(id).orElse(null);
	}
	
	
	public String deleteIssueUser(int id)
	{
		issueUserRepo.deleteById(id);	
		return "IssueUser deleted + " + id;
	}
	
	public IssueUser updateIssueUser(IssueUser issueUser)
	{
		IssueUser existingIssueUser = issueUserRepo.findById(issueUser.getId()).orElse(null);
		return issueUserRepo.save(existingIssueUser);
	}
	
	public List<IssueUserResponse> getAllCards(int issueId)
	{
		Issue gettedIssue = issueService.findIssueById(issueId);
		if(gettedIssue == null || gettedIssue.getIsRevealed() == false)
			return null;
		
		List<IssueUserResponse> responses = new ArrayList<>();
		
		List<IssueUser> users = issueUserRepo.findByIssueId(issueId);
		
		for(IssueUser usr: users)
		{
			IssueUserResponse isr = new IssueUserResponse();
			isr.setIssueId(usr.getIssue().getId());
			isr.setUserId(usr.getUser().getId());
			isr.setPoint(usr.getPoint());
			responses.add(isr);
		}
		
		return responses;
	}
	
	public void revealCards(int issueId)
	{
		Issue gettedIssue = issueService.findIssueById(issueId);
		gettedIssue.setIsRevealed(true);
		issueService.saveIssue(gettedIssue);
		List<IssueUser> issues = issueUserRepo.findByIssueId(gettedIssue.getId());
		
		double point = 0;
		
		for(IssueUser iUser: issues)
			point += iUser.getPoint();
		
		point = (issues.size() <= 0) ? 0 : point / issues.size();
		gettedIssue.setStoryPoint(point);
		issueService.saveIssue(gettedIssue);
	}
}
