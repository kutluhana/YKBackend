package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Issue;
import com.example.demo.service.IssueService;

@RestController
public class IssueController {
	
	@Autowired
	IssueService issueService;
	
	@PostMapping("/addIssue")
	public  Issue addIssue(@RequestBody Issue issue)
	{
		return issueService.saveIssue(issue);
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
	
	@GetMapping("/issue/{name}")
	public Issue findIssueByName(@PathVariable String name)
	{
		return issueService.getIssueByName(name);
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
	
}
