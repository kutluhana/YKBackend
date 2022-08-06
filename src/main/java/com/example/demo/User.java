package com.example.demo;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	//User 1(creates)n Deck
	@OneToMany(mappedBy="user")
	private List<Deck> decks;

	//User 1(creates)n Issue
	@OneToMany(mappedBy="user")
	private List<Issue> issues;
	
	//User 1(creates)n Game
	@OneToMany(mappedBy="user")
	private List<Game> games;
	
	//User n(plays_in)1 Game
	@ManyToOne
	@JoinColumn(name="game_id")
	private Game game;
	
	//User n(votes)m Issue
	@ManyToMany
	@JoinTable(name = "user_votes", 
	joinColumns = @JoinColumn(name = "user_id"),
	inverseJoinColumns = @JoinColumn(name = "issue_id"))
	private List<Issue> issuez;
	
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

	public List<Deck> getDecks() {
		return decks;
	}

	public void setDecks(List<Deck> decks) {
		this.decks = decks;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public List<Issue> getIssuez() {
		return issuez;
	}

	public void setIssuez(List<Issue> issuez) {
		this.issuez = issuez;
	}

	
}
