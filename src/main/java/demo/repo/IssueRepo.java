package demo.repo;



import org.springframework.data.jpa.repository.JpaRepository;

import demo.entity.Issue;



public interface IssueRepo extends JpaRepository<Issue, Integer> {


}