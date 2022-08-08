package demo.response;

import lombok.Data;

@Data
public class IssueUserResponse {
	private int issueId;
	
	private int userId;
	
	private int point;
}
