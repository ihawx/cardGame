package com.assemblewars.game;


import com.assemblewars.gamestates.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class MainGameClass extends ApplicationAdapter {

    private GameStateManager gsm;
    private SpriteBatch sb;
    private BitmapFont font;


    @Override
    public void create() {

        gsm = new GameStateManager();
        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 17;
        font = gen.generateFont(parameter);
        gen.dispose();       


    }

    @Override
    public void render() {
        gsm.update();
        gsm.draw();
        int W = Gdx.graphics.getWidth();
        int H = Gdx.graphics.getHeight();        
        sb.begin();
        font.setColor(Color.WHITE);
        font.draw(sb, Integer.toString(Gdx.graphics.getFramesPerSecond()), 0, H - 10);
        sb.end();
    }

}
