package demo.entity;

import java.util.Comparator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="issue")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Issue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String issueName;


	private String description;
	
	private int storyPoint;
	
	private Boolean isRevealed;
	
	@OneToOne(mappedBy = "selectedIssue")
	@JsonBackReference
	private Game game;
	
	@ManyToOne
	@JoinColumn(name="owner_id", nullable=false)
	@JsonManagedReference
	@JsonIgnoreProperties("selectedIssue")
	private Game ownerGame;
	
	 @Override
	 public int hashCode()
	 {
		 return id;
	 }


	 

}
