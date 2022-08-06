package repo;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Issue;
import entity.User;

public interface IssueRepo extends JpaRepository<Issue, Integer> {

	Issue findByName(String name);
}