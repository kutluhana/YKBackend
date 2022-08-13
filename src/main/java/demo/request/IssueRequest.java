package demo.request;

import lombok.Data;

@Data
public class IssueRequest {
	private String description;
	
	private String issueName;
}
