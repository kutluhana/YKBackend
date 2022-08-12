package demo.response;

import java.util.List;

import demo.entity.Game;
import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.entity.User;

public class SocketAllResponse {
private Game game;
	
	private List<User> users;
	
	private List<Issue> issues;
	
	private  List<IssueUser> issuePoints;
	
	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<IssueUser> getIssuePoints() {
		return issuePoints;
	}

	public void setIssuePoints(List<IssueUser> issuePoints) {
		this.issuePoints = issuePoints;
	}

	
}
