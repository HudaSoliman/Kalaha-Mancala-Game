package com.kalaha.model;

import java.util.ArrayList;
import java.util.List;

public class Board {

	public static final int PIT_START_INDEX = 1;
	public static final int PIT_END_INDEX = 14;

	private final List<Pit> pits;

	public Board() {
		this.pits = new ArrayList<>();
		for (int i = Board.PIT_START_INDEX; i <= Board.PIT_END_INDEX; i++) {
			this.pits.add(new Pit(i));
		}
	}

	public Board(List<Pit> pits) {
		this.pits = pits;
		for (int i = Board.PIT_START_INDEX; i <= Board.PIT_END_INDEX; i++) {
			this.pits.add(new Pit(i));
		}
	}

	public Pit getPit(int index) {
		return this.pits.get((index - 1) % Board.PIT_END_INDEX);
	}

	public List<Pit> getPits() {
		return this.pits;
	}

	public Pit getOppositePit(int pitIndex) {
		return getPit(Board.PIT_END_INDEX - (pitIndex % Board.PIT_END_INDEX));
	}

	public int getStonesCount(Player player, boolean includeHome) {
		return this.getPits().stream().filter(
				pit -> pit.getId() != null && (pit.getOwner().equals(player) && (includeHome || !pit.isHomePit())))
				.mapToInt(Pit::getStonesCount).sum();
	}
}
