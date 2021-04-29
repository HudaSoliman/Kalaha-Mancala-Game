package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;

public interface GameService {

    Game createGame();

    Game play(Long gameId, Long pitId);

	Game findGame(Long gameId);

}
