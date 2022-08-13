package demo.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="game")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Game {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int id;
		
		@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
		@JoinColumn(name = "selected_issue_id", referencedColumnName="id")
		@JsonManagedReference
		private Issue selectedIssue;
		
		private String gameStatus;
		
		private String gameName;
		
		@OneToMany(fetch = FetchType.LAZY,mappedBy="ownerGame")
		@JsonBackReference
		private Set<Issue> issuesInGame;
		
		@OneToMany(fetch = FetchType.LAZY,mappedBy="inGame")
		@JsonBackReference
		private Set<User> users;
		
		 @Override
		 public int hashCode()
		 {
			 return id;
		 }
		 
}
