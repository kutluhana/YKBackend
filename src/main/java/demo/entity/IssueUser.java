package demo.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="issueuser")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class IssueUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="issue_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonManagedReference
	Issue issue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable=false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonManagedReference
	User user;
	
	int point;
	
	 @Override
	 public int hashCode()
	 {
		 return id;
	 }
	 
	 @Override
	 public boolean equals(Object obj)
	 {
		 if(this.getClass() != obj.getClass())
			 return false;
		 
		 if(obj == null)
			 return false;
		 
		 IssueUser other = (IssueUser) obj;
		 return this.getId() == other.getId();
	 }
}
