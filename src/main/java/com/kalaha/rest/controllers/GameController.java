package com.kalaha.rest.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.kalaha.model.Game;
import com.kalaha.repository.GameRepository;
import com.kalaha.rest.resources.GameModel;
import com.kalaha.service.GameService;

import io.swagger.annotations.ApiOperation;

@EnableAutoConfiguration
@RestController
@ExposesResourceFor(Game.class)
@RequestMapping("/api/games")
public class GameController {

	@Autowired
	GameRepository gameRepository;

	@Autowired
	GameService gameService;

	@CrossOrigin
	@ApiOperation(value = "Get game", nickname = "getGame")
	@GetMapping("/{id}")
	public ResponseEntity<GameModel> getGame(@PathVariable(name = "id") Long id) {

		final Game game = gameService.findGame(id);
		final GameModel gameResponse = new GameModel(game);

		return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "create game", nickname = "createGame")
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameModel> createGame() {

		final Game game = gameService.createGame();
		final GameModel gameResponse = new GameModel(game);

		return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);
	}

	@CrossOrigin
	@PutMapping("/{gameId}/pits/{pitId}")
	public ResponseEntity<GameModel> playGame(@PathVariable final Long gameId, @PathVariable final Long pitId) {

		Game game = gameService.play(gameId, pitId);

		game.setPits(game.getPits().stream().filter(pit -> pit.getId() != null).collect(Collectors.toList()));
		Game g = gameRepository.save(game);

		GameModel gameResponse = new GameModel(g);
		return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
	}

}
