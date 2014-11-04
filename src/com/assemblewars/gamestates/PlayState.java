package com.assemblewars.gamestates;

import com.assemblewars.cards.UnitCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.ArrayList;

public class PlayState extends GameState {

    private ArrayList<UnitCard> unit;
    int W, H;
    ShapeRenderer sr;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
        unit = new ArrayList<UnitCard>();
        sr = new ShapeRenderer();
    }

    public void init() {

    }

    public void update() {
        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for(int j =0; j<2; j++)
            for (int i = 1; i < 7; i++) {
                unit.add(new UnitCard(10, -100, 2000000 + i));
            }
        }
        centerHand();
        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).update();
        }
    }

    public void centerHand() {
        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        int coordX = Gdx.input.getX();
        int coordY = Gdx.input.getY();
        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).setPosition(W / 2 - 75 * unit.size() / 2 + i * 75, -100);
            if (i < unit.size() - 1) {
                if (coordX > W / 2 - 75 * unit.size() / 2 + i * 75 && coordX < W / 2 - 75 * unit.size() / 2 + 75 + i * 75 && coordY > H - 100) {
                    for (int j = 0; j <= i; j++) {
                        unit.get(j).setPosition(W / 2 - 75 * unit.size() / 2 + j * 75 - 75, -100);
                        if (j == i) {
                            unit.get(j).setPosition(W / 2 - 75 * unit.size() / 2 + j * 75 - 75, 0);
                        }
                    }
                }
            }
            if (i == unit.size() - 1) {
                if (coordX > W / 2 - 75 * unit.size() / 2 + i * 75 && coordX < W / 2 - 75 * unit.size() / 2 + 150 + i * 75 && coordY > H - 100) {
                    unit.get(i).setPosition(W / 2 - 75 * unit.size() / 2 + i * 75, 0);
                }
            }
        }
    }

    public void draw() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).draw(sr);
        }

    }

    public void dispose() {

    }

}
