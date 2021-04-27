package com.bol.kalaha.service;

import com.bol.kalaha.model.Game;

public interface GameService {

    Game createGame();

    Game play(long gameId, long pitId);

}
