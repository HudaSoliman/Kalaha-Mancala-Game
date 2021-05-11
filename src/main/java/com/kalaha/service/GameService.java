package com.kalaha.service;

import com.kalaha.model.Game;

public interface GameService {

    Game createGame();

    Game play(Long gameId, Long pitId);

	Game findGame(Long gameId);

}
