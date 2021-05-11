package com.kalaha.rest.resources;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kalaha.model.Pit;
import com.kalaha.model.Player;

public class PitModel extends RepresentationModel {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("index")
	private int index;
	@JsonProperty("owner")
	private Player owner;
	@JsonProperty("stones_count")
	private int stonesCount;
	@JsonProperty("is_home")
	private boolean isHome;
	@JsonProperty("game_id")
	private Long gameId;

	public PitModel(Long id) {
		this.id = id;
	}

	public PitModel(Pit pit) {
		setId(pit.getId());
		setIndex(pit.getIndex());
		setOwner(pit.getOwner());
		setStonesCount(pit.getStonesCount());
		setHome(pit.isHomePit());
		setGameId(pit.getGame().getId());

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Player getPlayerId() {
		return owner;
	}

	public void setOwner(Player playerId) {
		this.owner = playerId;
	}

	public int getStonesCount() {
		return stonesCount;
	}

	public void setStonesCount(int stonesCount) {
		this.stonesCount = stonesCount;
	}

	public boolean isHome() {
		return isHome;
	}

	public void setHome(boolean isHome) {
		this.isHome = isHome;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

}
