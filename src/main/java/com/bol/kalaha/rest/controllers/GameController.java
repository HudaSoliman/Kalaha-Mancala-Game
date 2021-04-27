package com.bol.kalaha.rest.controllers;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bol.kalaha.model.Game;
import com.bol.kalaha.repository.GameRepository;
import com.bol.kalaha.repository.PitRepository;
import com.bol.kalaha.rest.resources.GameModel;
import com.bol.kalaha.service.GameService;

import io.swagger.annotations.ApiOperation;

//@CrossOrigin(origins = "http://localhost:4200")
@EnableAutoConfiguration
@RestController
@ExposesResourceFor(Game.class)
@RequestMapping("/api")
public class GameController {

	@Autowired
	PitRepository pitRepository;

	@Autowired
	GameRepository gameRepository;

	@Autowired
	GameService gameService;

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "create game", nickname = "createGame")
	@PostMapping(name = "/games", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GameModel> createGame() {

		final Game game = gameService.createGame();
		final GameModel gameResponse = new GameModel(game);

		return ResponseEntity.status(HttpStatus.CREATED).body(gameResponse);
	}

	@CrossOrigin
	@PatchMapping("/games/{gameId}/pits/{pitId}")
	public ResponseEntity<GameModel> playGame(@PathVariable final long gameId, @PathVariable final long pitId) {
		
		Game game = gameService.play(gameId, pitId);
		game.setPits(game.getPits().stream().filter(pit -> pit.getId() != null).collect(Collectors.toList()));
		gameRepository.save(game);
		
		GameModel gameResponse = new GameModel(game);

		return ResponseEntity.status(HttpStatus.OK).body(gameResponse);
	}

}
