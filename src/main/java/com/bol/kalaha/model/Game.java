package com.bol.kalaha.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "game")
public class Game {
	@Id
	@GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "game", cascade = { CascadeType.ALL })
	private List<Pit> pits;

	private Player winner;

	private Player turn;

	public Game() {
		this.winner = null;
		this.turn = Player.FIRST_PLAYER;
		this.pits = new Board().getPits();
		for (Pit p : this.pits) {
			p.setGame(this);
		}
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

	public Board getBoard() {
		return new Board(this.pits);
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

	@Override
	public String toString() {
		return "Game [id=" + id + ", winner=" + winner + ", pits=" + pits + ", turn=" + turn + "]";
	}
}
