package com.assemblewars.gamestates;

import com.assemblewars.cards.Card;
import com.assemblewars.cards.UnitCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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
            for (int i = 0; i < 10; i++) {
                unit.add(new UnitCard(10, -100, 2000000 + (int) MathUtils.random(1, 33)));
            }
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            int coordX = Gdx.input.getX();
            int coordY = Gdx.input.getY();
            for (int i = 0; i < unit.size(); i++) {
                if (coordX > unit.get(i).getX() && coordX < unit.get(i).getX() + Card.getCardsWidth()
                        && coordY < H - unit.get(i).getY() && coordY > H - unit.get(i).getY() - Card.getCardsHeight()) {
                    unit.get(i).setZoom(true);
                } else {
                    unit.get(i).setZoom(false);
                }
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
        int center = (2 * W - Card.getCardsWidth() * unit.size()) / 4;
        for (int i = 0; i < unit.size(); i++) {
            if (unit.get(i).getZoomed() == false) {
                unit.get(i).setPosition(center + i * Card.getCardsWidth() / 2, -100);
                if (i < unit.size() - 1) {
                    if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + (Card.getCardsWidth() / 2) * (1 + i) && coordY > H - 100) {
                        for (int j = 0; j <= i; j++) {
                            unit.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), -100);
                            if (j == i) {
                                unit.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), 0);
                            }
                        }
                    }
                }
                if (i == unit.size() - 1) {
                    if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + Card.getCardsWidth() + i * Card.getCardsWidth() / 2 && coordY > H - 100) {
                        unit.get(i).setPosition(center + i * Card.getCardsWidth() / 2, 0);
                    }
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
