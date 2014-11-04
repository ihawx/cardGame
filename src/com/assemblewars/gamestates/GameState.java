
package com.assemblewars.gamestates;

public abstract class GameState {
    
    protected GameStateManager gsm;
    
    protected GameState(GameStateManager gsm){
        this.gsm=gsm;
    }
    
    public abstract void init();
    public abstract void update();
    public abstract void draw();
    public abstract void dispose();
}
