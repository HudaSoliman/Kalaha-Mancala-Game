package com.kalaha.rest.resources;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kalaha.model.Game;
import com.kalaha.model.Pit;
import com.kalaha.model.Player;

public class GameModel extends RepresentationModel {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("pits")
	private List<Pit> pits;
	@JsonProperty("winner")
	private Player winner;
	@JsonProperty("turn")
	private Player turn;

	public GameModel(Long id) {
		this.id = id;
	}

	public GameModel(Game game) {
		setId(game.getId());
		setPits(game.getPits());
		setWinner(game.getWinner());
		setTurn(game.getTurn());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}

	public Player getTurn() {
		return turn;
	}

	public void setTurn(Player turn) {
		this.turn = turn;
	}


}
