package repo;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.IssueUser;
import entity.IssueUserPK;

public interface IssueUserRepo extends JpaRepository<IssueUser, IssueUserPK> {

	IssueUser findByName(String name);
}
