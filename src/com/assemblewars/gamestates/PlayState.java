package com.assemblewars.gamestates;

import com.assemblewars.cards.Card;
import com.assemblewars.cards.UnitCard;
import com.assemblewars.game.Screenshots;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlayState extends GameState {

    private ArrayList<UnitCard> unit;
    public static int W, H, beingMoved;
    private boolean isPressed = false;
    ShapeRenderer sr;
    private SpriteBatch sb;
    private BitmapFont smallFont;
    private BitmapFont standartFont;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        init();
        unit = new ArrayList<UnitCard>();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        smallFont = gen.generateFont(parameter);
        parameter.size = 40;
        standartFont = gen.generateFont(parameter);
        gen.dispose();
    }

    public void init() {

    }

    public void update() {

        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            for (int i = 0; i < 8; i++) {
                unit.add(new UnitCard(10, -100, 2000000 + (int) MathUtils.random(1, 33)));
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Screenshots.saveScreenshot();
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
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            isPressed = false;
            beingMoved = 0;
        }
        if (isPressed) {
            int coordX = Gdx.input.getX();
            int coordY = H - Gdx.input.getY();
            unit.get(beingMoved).setState(2);
            unit.get(beingMoved).setPosition(coordX - Card.getCardsWidth() / 2, coordY - Card.getCardsHeight() / 2);
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && isPressed == false) {
            isPressed = true;
            int coordX = Gdx.input.getX();
            int coordY = H - Gdx.input.getY();
            for (int i = 0; i < unit.size(); i++) {
                if (coordX > unit.get(i).getX() && coordX < unit.get(i).getX() + Card.getCardsWidth()
                        && coordY > unit.get(i).getY() && coordY < unit.get(i).getY() + Card.getCardsHeight()) {
                    beingMoved = i;
                    break;
                }
            }
        }

        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).update();
        }
        centerHand();
    }

    public void centerHand() {
        if (!isPressed) {
            W = Gdx.graphics.getWidth();
            H = Gdx.graphics.getHeight();
            int cid = 0; //cards in deck
            int coordX = Gdx.input.getX();
            int coordY = Gdx.input.getY();            
            Collections.sort(unit, new Comparator<UnitCard>() {
                public int compare(UnitCard uc1, UnitCard uc2) {
                    return uc1.getState() - uc2.getState();
                }
            });
            for (int i = 0; i < unit.size(); i++) {
                if (unit.get(i).getState() == 1) {
                    cid++;
                }
            }
            int center = (2 * W - Card.getCardsWidth() * cid) / 4; 
            //System.out.println(cid);
            for (int i = 0; i < cid; i++) {
                if (unit.get(i).getState() == 1) {
                    unit.get(i).setPosition(center + i * Card.getCardsWidth() / 2, -100);
                    if (i < cid - 1) {
                        if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + (Card.getCardsWidth() / 2) * (1 + i) && coordY > H - 100) {
                            for (int j = 0; j <= i; j++) {
                                if (unit.get(j).getState() == 1) {
                                    unit.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), -100);
                                    if (j == i) {
                                        unit.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), 0);
                                    }
                                }
                            }
                        }
                    }
                    if (i == cid - 1) {
                        if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + Card.getCardsWidth() + i * Card.getCardsWidth() / 2 && coordY > H - 100) {
                            unit.get(i).setPosition(center + i * Card.getCardsWidth() / 2, 0);
                        }
                    }
                }

            }
        }
    }

    public void draw() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //TABLE OUTLINE
        sr.begin(ShapeType.Line);
        sr.setColor(Color.WHITE);
        sr.line(0, H / 2, W, H / 2);
        sb.begin();
        sr.box(W - Card.getCardsHeight() - 20, 15, 0, Card.getCardsHeight(), Card.getCardsWidth(), 0);
        standartFont.draw(sb, "DECK", W - Card.getCardsHeight(), 15 + Card.getCardsWidth() / 2);
        sr.box(W - Card.getCardsHeight() - 20, 20 + Card.getCardsWidth(), 0, Card.getCardsHeight(), Card.getCardsWidth(), 0);
        standartFont.draw(sb, "EFFECTS", W - Card.getCardsHeight(), 20 + 3 * Card.getCardsWidth() / 2);
        sb.end();
        sr.end();

        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).draw(sr);
        }
        for (int i = 0; i < unit.size(); i++) {
            if (unit.get(i).getZoomed() == true) {
                unit.get(i).draw(sr);
            }
        }

    }

    public void dispose() {

    }

}
