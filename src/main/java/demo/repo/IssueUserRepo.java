package demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.entity.IssueUser;


public interface IssueUserRepo extends JpaRepository<IssueUser, Integer> {
	
	List<IssueUser> findByUserId(int userId);
	
	List<IssueUser> findByIssueId(int issueId);
}
