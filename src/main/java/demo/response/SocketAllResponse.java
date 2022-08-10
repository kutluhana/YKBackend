package demo.response;

import java.util.List;

import demo.entity.Game;
import demo.entity.Issue;
import demo.entity.IssueUser;
import demo.entity.User;
import lombok.Data;

@Data
public class SocketAllResponse {
	private Game game;
	
	private List<User> users;
	
	private List<Issue> issues;
	
	private  List<IssueUser> issuePoints;
}
