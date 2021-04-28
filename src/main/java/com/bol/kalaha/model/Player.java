package com.bol.kalaha.model;

public enum Player {
    FIRST_PLAYER(Board.PIT_END_INDEX),
    SECOND_PLAYER(Board.PIT_END_INDEX / 2);

    private int homeIndex;

    Player(final int homeIndex) {
        this.homeIndex = homeIndex;
    }

    public int getHomeIndex() {
        return this.homeIndex;
    }
    
    static public Player getPlayer(long index) {
    	if(index >= Board.PIT_START_INDEX && index <=Board.PIT_END_INDEX/2 ) {
    		return SECOND_PLAYER;
    	}
    	return FIRST_PLAYER;
    }
}
