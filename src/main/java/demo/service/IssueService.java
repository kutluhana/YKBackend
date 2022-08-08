package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.entity.Issue;
import demo.repo.IssueRepo;

@Service
public class IssueService {

	@Autowired
	IssueRepo issueRepo;
	
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
}
