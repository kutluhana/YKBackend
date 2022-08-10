package demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="user")
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int id;

	 private String name;
	 
	 @ManyToOne
	 @JoinColumn(name="game_id", nullable=false)
	 private Game inGame;
	 
	 @Override
	 public int hashCode()
	 {
		 return id;
	 }
}
