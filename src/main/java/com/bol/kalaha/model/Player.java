package com.bol.kalaha.model;

public enum Player {
    SECOND_PLAYER(Board.PIT_END_INDEX / 2),
    FIRST_PLAYER(Board.PIT_END_INDEX);

    private int homeIndex;

    Player(final int homeIndex) {
        this.homeIndex = homeIndex;
    }

    public int getHomeIndex() {
        return this.homeIndex;
    }
    
    static public Player getPlayer(long index) {
    	if(index >= Board.PIT_START_INDEX && index <=Board.PIT_END_INDEX/2 ) {
    		return FIRST_PLAYER;
    	}
    	return SECOND_PLAYER;
    }
}
