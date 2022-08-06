package service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import entity.IssueUser;
import repo.IssueRepo;
import repo.IssueUserRepo;

public class IssueUserService {

	@Autowired
	IssueUserRepo issueUserRepo;
	
	public IssueUser saveIssueUser(IssueUser issueUser)
	{
		return issueUserRepo.save(issueUser);
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
	
	public IssueUser getIssueUserByName(String name)
	{
		return issueUserRepo.findByName(name);
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
}
