package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Issue;
import com.example.demo.repository.IssueRepository;

@Service
public class IssueService {
	
	@Autowired
	IssueRepository issueRepository;
	
	public Issue saveIssue(Issue issue)
	{
		return issueRepository.save(issue);
	}
	
	public List<Issue> saveIssues(List<Issue> issues)
	{
		return issueRepository.saveAll(issues);
	}
	
	public List<Issue> getIssues()
	{
		return issueRepository.findAll();
	}
	
	public Issue findIssueById(int id)
	{
		return issueRepository.findById(id).orElse(null);
	}
	
	public Issue getIssueByName(String name)
	{
		return issueRepository.findByName(name);
	}
	
	public String deleteIssue(int id)
	{
		issueRepository.deleteById(id);	
		return "Issue deleted + " + id;
	}
	
	public Issue updateIssue(Issue issue)
	{
		Issue existingIssue = issueRepository.findById(issue.getID()).orElse(null);
		existingIssue.setName(issue.getName());
		existingIssue.setDescription(issue.getDescription());
		return issueRepository.save(existingIssue);
	}
}
