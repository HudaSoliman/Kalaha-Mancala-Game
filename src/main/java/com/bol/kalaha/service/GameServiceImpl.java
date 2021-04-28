package com.bol.kalaha.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bol.kalaha.model.Board;
import com.bol.kalaha.model.Game;
import com.bol.kalaha.model.Pit;
import com.bol.kalaha.model.Player;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.repository.PitRepository;
import com.bol.kalaha.rest.exception.GameNotFoundException;
import com.bol.kalaha.rest.exception.IllegalMoveException;
import com.bol.kalaha.rest.exception.IllegalMoveType;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository gameRepository;

	@Autowired
	private PitRepository pitRepository;

	public GameServiceImpl(GameRepository repository) {
	}

	@Override
	@Transactional
	public Game createGame() {
		Game game = new Game();
		return gameRepository.save(game);
	}

	@Override
	public Game play(long gameId, long pitId) {
		Optional<Game> g = gameRepository.findById(gameId);
		if (!g.isPresent()) {
			throw new GameNotFoundException(gameId);
		}
		Game game = g.get();
		Optional<Pit> pit = pitRepository.findById(pitId);
		if (pit.isPresent()) {
			moveStones(game, pit.get().getIndex());
			checkGameOver(game);
		}
		return game;
	}

	private void clearBoard(Game game) {
		game.getBoard().getPits().parallelStream()
				.filter(pit -> (Player.FIRST_PLAYER.getHomeIndex() != pit.getIndex())
						&& (Player.SECOND_PLAYER.getHomeIndex() != pit.getIndex()))
				.forEach(pit -> pit.setStonesCount(0));
	}

	private void moveStones(Game game, int pitIndex) {
		Pit startPit = game.getBoard().getPit(pitIndex);
		validateMove(game, pitIndex);
		int stoneToDistribute = startPit.getStonesCount();
		startPit.setStonesCount(0);
		while (stoneToDistribute > 0) {
			Pit currentPit = game.getBoard().getPit(++pitIndex);
			if (currentPit.isDistributable(game.getTurn())) {
				currentPit.setStonesCount(currentPit.getStonesCount() + 1);
				stoneToDistribute--;

			}
		}
		lastEmptyPit(game, pitIndex);
		decidePlayerTurn(game, pitIndex);
	}

	private void checkGameOver(Game game) {
		int firstPlayerPitStoneCount = game.getBoard().getStonesCount(Player.FIRST_PLAYER, false);
		int secondPlayerPitStoneCount = game.getBoard().getStonesCount(Player.SECOND_PLAYER, false);
		if ((firstPlayerPitStoneCount == 0) || (secondPlayerPitStoneCount == 0)) {
			Pit homeFirstPlayer = game.getBoard().getPit(Player.FIRST_PLAYER.getHomeIndex());
			Pit homeSecondPlayer = game.getBoard().getPit(Player.SECOND_PLAYER.getHomeIndex());
			homeFirstPlayer.setStonesCount(homeFirstPlayer.getStonesCount() + firstPlayerPitStoneCount);
			homeSecondPlayer.setStonesCount(homeSecondPlayer.getStonesCount() + secondPlayerPitStoneCount);
			determineWinner(game);
			clearBoard(game);
		}
	}

	private void determineWinner(Game game) {
		int homeFirstStoneCount = game.getBoard().getStonesCount(Player.FIRST_PLAYER, true);
		int homeSecondStoneCount = game.getBoard().getStonesCount(Player.SECOND_PLAYER, true);
		if (homeFirstStoneCount > homeSecondStoneCount) {
			game.setWinner(Player.FIRST_PLAYER);
		} else if (homeFirstStoneCount < homeSecondStoneCount) {
			game.setWinner(Player.SECOND_PLAYER);
		}
	}

	private void lastEmptyPit(Game game, int lastIndex) {

		Pit lastPit = game.getBoard().getPit(lastIndex);
		if (!lastPit.isHomePit() && lastPit.getOwner().equals(game.getTurn()) && (lastPit.getStonesCount() == 1)) {
			Pit oppositePit = game.getBoard().getOppositePit(lastIndex);

//			if (oppositePit.getStonesCount() > 0) {
				Pit homePit = game.getBoard().getPit(lastPit.getOwner().getHomeIndex());
				homePit.setStonesCount(
						(homePit.getStonesCount() + oppositePit.getStonesCount()) + lastPit.getStonesCount());
				oppositePit.setStonesCount(0);
				lastPit.setStonesCount(0);
//			}
		}
	}

	private void decidePlayerTurn(Game game, int pitIndex) {
		Pit pit = game.getBoard().getPit(pitIndex);

		if (pit.isHomePit() && Player.FIRST_PLAYER.equals(pit.getOwner())
				&& Player.FIRST_PLAYER.equals(game.getTurn())) {
			game.setTurn(Player.FIRST_PLAYER);
		} else if (pit.isHomePit() && Player.SECOND_PLAYER.equals(pit.getOwner())
				&& Player.SECOND_PLAYER.equals(game.getTurn())) {
			game.setTurn(Player.SECOND_PLAYER);
		} else {
			if (Player.FIRST_PLAYER.equals(game.getTurn())) {
				game.setTurn(Player.SECOND_PLAYER);
			} else {
				game.setTurn(Player.FIRST_PLAYER);
			}
		}
	}

	private void validateMove(Game game, int pitIndex) {
		Pit pit = game.getBoard().getPit(pitIndex);
		if (pit.isHomePit()) {
			throw new IllegalMoveException(IllegalMoveType.HOME_PIT_START_INDEX);
		}
		if (Player.FIRST_PLAYER.equals(game.getTurn()) && !Player.FIRST_PLAYER.equals(pit.getOwner())) {
			throw new IllegalMoveException(IllegalMoveType.SECOND_PLAYER_TURN);
		}
		if (Player.SECOND_PLAYER.equals(game.getTurn()) && !Player.SECOND_PLAYER.equals(pit.getOwner())) {
			throw new IllegalMoveException(IllegalMoveType.FIRST_PLAYER_TURN);
		}
		if (pit.getStonesCount() == 0) {
			throw new IllegalMoveException(IllegalMoveType.SELECTED_EMPTY_PIT);
		}
		if (game.getTurn() == null) {
			if (Player.FIRST_PLAYER.equals(pit.getOwner())) {
				game.setTurn(Player.FIRST_PLAYER);
			} else {
				game.setTurn(Player.SECOND_PLAYER);
			}
		}
	}
}
