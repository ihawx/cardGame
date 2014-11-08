package com.assemblewars.gamestates;


public class GameStateManager {

    private GameState gameState;

    public static final int MENU = 0;
    public static final int PLAY = 1;
    public static final int DECKBUILD = 2;


    public GameStateManager() {        
        setState(DECKBUILD);
    }

    public void setState(int state) {
        if (gameState != null) {
            gameState.dispose();
        }
        if (state == MENU) {
            //gameState = new MenuState(this);
        }
        if (state == PLAY) {
            gameState = new PlayState(this);
        }
        if (state == DECKBUILD) {
            gameState = new DeckBuildState(this);
        }
    }

    public void update() {
        gameState.update();
    }

    public void draw() {
        gameState.draw();
    }
}
