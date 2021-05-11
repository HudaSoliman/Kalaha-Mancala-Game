package com.kalaha.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pit")
public class Pit {

	@Id
	@SequenceGenerator(name = "pit_seq", sequenceName = "seq_pit", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pit_seq")
	private Long id;

	private int index;

	@NotNull
	@JoinColumn(name = "player_id", nullable = false)
	private Player owner;

	@Column(name = "stones_count")
	private int stonesCount;

	@Column(name = "is_home")
	private boolean isHome;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "game_id", nullable = false)
	private Game game;

	public Pit() {
		this.isHome = this.isHomePit();
		this.owner = Player.getPlayer(index);
		if (!this.isHomePit()) {
			this.setStonesCount(6);
		}
	}

	public Pit(int index) {
		this.index = index;
		this.isHome = this.isHomePit();
		this.owner = Player.getPlayer(index);
		if (!this.isHomePit()) {
			this.setStonesCount(6);
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player player) {
		this.owner = player;
	}

	public int getStonesCount() {
		return stonesCount;
	}

	public void setStonesCount(int stonesCount) {
		this.stonesCount = stonesCount;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isDistributable(final Player turn) {
		return turn.equals(this.owner) || !isHomePit();
	}

	public boolean isHomePit() {
		return (this.getIndex() == Player.FIRST_PLAYER.getHomeIndex())
				|| (this.getIndex() == Player.SECOND_PLAYER.getHomeIndex());
	}

	@Override
	public String toString() {
		return "Pit [id=" + id + ", stonesCount=" + stonesCount + ", isHome=" + isHome + ", index=" + index + ", owner="
				+ owner + "]";
	}

}