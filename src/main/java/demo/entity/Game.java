package demo.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="game")
@Data
public class Game {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@OneToOne(cascade=CascadeType.ALL)
		@JoinColumn(name = "selected_issue_id", referencedColumnName="id")
		private Issue selectedIssue;
		
		private String gameStatus;
		
		@OneToMany(mappedBy="ownerGame")
		private Set<Issue> issuesInGame;
		
		@OneToMany(mappedBy="inGame")
		private Set<User> users;
		
}
