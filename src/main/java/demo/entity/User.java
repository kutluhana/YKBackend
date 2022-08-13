package demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Table(name="user")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;

	 private String name;
	 
	 @ManyToOne
	 @JoinColumn(name="game_id", nullable=false)
	 @JsonManagedReference
	 private Game inGame;
	 
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
		 
		 User other = (User) obj;
		 return this.getId() == other.getId();
	 }
}
