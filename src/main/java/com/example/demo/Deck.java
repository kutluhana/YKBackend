package com.example.demo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Deck {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private Date createdAt;
	
	@Column
	private Date updatedAt;
	
	//Game 1(uses)1 Deck
	@OneToOne(mappedBy="deck")
	private Game game;
	
	//User 1(creates)n Deck
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	/*
	//Deck 1(has)n values
	@OneToMany(mappedBy="deck")
	private List<DeckValues> deckValues;*/
	
	/*//Deck has many values
	@JoinTable(name = "deck_values", 
			joinColumns = @JoinColumn(name = "deck_value"))
	private List<Integer> deckValues;*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	

	
}
