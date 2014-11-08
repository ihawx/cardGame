package com.assemblewars.gamestates;

import com.assemblewars.cards.Card;
import com.assemblewars.cards.CommanderCard;
import com.assemblewars.cards.UnitCard;
import com.assemblewars.filehandling.FileHandling;
import com.assemblewars.game.Screenshots;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayState extends GameState {

    private ArrayList<UnitCard> unit;
    private ArrayList<CommanderCard> commander;
    public static int W, H, beingMoved;
    private boolean isPressed = false;
    ShapeRenderer sr;
    private SpriteBatch sb;
    private BitmapFont smallFont;
    private BitmapFont standartFont;

    private Sound[] cardSounds = new Sound[2];


    private Texture background;
    private Texture star;

    public static final int MAX_CARDS_IN_HAND = 8;
    public static int cardsInHand = 0;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        unit = new ArrayList<UnitCard>();
        commander = new ArrayList<CommanderCard>();
        sr = new ShapeRenderer();
        sb = new SpriteBatch();
        //
        background = new Texture(Gdx.files.internal("Graphics/Background.png"));
        star = new Texture(Gdx.files.internal("Graphics/star_gold.png"));
        //
        cardSounds[0] = Gdx.audio.newSound(Gdx.files.internal("cardPlace2.ogg"));
        cardSounds[1] = Gdx.audio.newSound(Gdx.files.internal("cardSlide3.ogg"));
        //
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        smallFont = gen.generateFont(parameter);
        parameter.size = 40;
        standartFont = gen.generateFont(parameter);
        gen.dispose();

        
        //
        init();
        //
        Gdx.input.setInputProcessor(new InputProcessor() {

            public boolean touchDown(int x, int y, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    y = H - y;
                    if (x > W - Card.getCardsWidth() - 20 && x < W - Card.getCardsWidth() - 20 + Card.getCardsWidth()
                            && y > 6 && y < 6 + Card.getCardsHeight() && cardsInHand < MAX_CARDS_IN_HAND) {
                        try {
                            unit.add(new UnitCard(0, 0, 2000000 + (int) MathUtils.random(1, FileHandling.countLines("Database/Cards/Units.txt"))));
                        } catch (IOException ex) {
                            Logger.getLogger(PlayState.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        cardsInHand++;
                        //cardSounds[1].play();
                        unit.get(unit.size() - 1).setState(1);
                        return true;
                    }
                }
                return false;
            }

            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }

        });
    }

    public void init() {
        for (int i = 0; i < 3; i++) {
            try {
                commander.add(new CommanderCard(0, 0, 1000000 + (int) MathUtils.random(1, FileHandling.countLines("Database/Cards/Commanders.txt"))));
            } catch (IOException ex) {
                Logger.getLogger(PlayState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void update() {


        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            System.exit(0);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Screenshots.saveScreenshot();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            unit.clear();
            cardsInHand = 0;
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
            for (int i = 0; i < commander.size(); i++) {
                if (coordX > commander.get(i).getX() && coordX < commander.get(i).getX() + Card.getCardsWidth()
                        && coordY < H - commander.get(i).getY() && coordY > H - commander.get(i).getY() - Card.getCardsHeight()) {
                    commander.get(i).setZoom(true);
                } else {
                    commander.get(i).setZoom(false);
                }
            }
        }
        if (!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && isPressed == true) {
            unit.get(beingMoved).setState(3);
            cardSounds[0].play();
            isPressed = false;
            beingMoved = -1;
        }

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && isPressed == false && unit.size() > 0) {
            int coordX = Gdx.input.getX();
            int coordY = H - Gdx.input.getY();
            for (int i = unit.size()-1; i >= 0; i--) {
                if (coordX > unit.get(i).getX() && coordX < unit.get(i).getX() + Card.getCardsWidth()
                        && coordY > unit.get(i).getY() && coordY < unit.get(i).getY() + Card.getCardsHeight()) {
                    beingMoved = i;
                    isPressed = true;
                    break;
                }
            }
        }
        if (isPressed) {
            int coordX = Gdx.input.getX();
            int coordY = H - Gdx.input.getY();
            if (unit.get(beingMoved).getState() == 1) {
                cardsInHand--;
            }
            unit.get(beingMoved).setState(2);
            //Collections.swap(unit, beingMoved, unit.size() - 1);
            //beingMoved = unit.size() - 1;
            unit.get(beingMoved).setPosition(coordX - Card.getCardsWidth() / 2, coordY - Card.getCardsHeight() / 2);
        }

        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).update();
        }

        for (int i = 0; i < commander.size(); i++) {
            commander.get(i).update();
        }
        centerHand();
        centerTable();
        centerCommanders();
    }

    public void centerHand() {
        if (!isPressed) {
            W = Gdx.graphics.getWidth();
            H = Gdx.graphics.getHeight();
            int cid = 0; //cards in hand
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

    public void centerCommanders() {
        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        int coordX = Gdx.input.getX();
        int coordY = Gdx.input.getY();
        int center = W/18;
        for (int i = 0; i < 3; i++) {
            commander.get(i).setPosition(center + i * Card.getCardsWidth() / 2, -100);
            if (i < 2) {
                if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + (Card.getCardsWidth() / 2) * (1 + i) && coordY > H - 100) {
                    for (int j = 0; j <= i; j++) {
                        commander.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), -100);
                        if (j == i) {
                            commander.get(j).setPosition(center + (Card.getCardsWidth() / 2) * (j - 1), 0);
                        }
                    }
                }
            }
            if (i == 2) {
                if (coordX > center + i * Card.getCardsWidth() / 2 && coordX < center + Card.getCardsWidth() + i * Card.getCardsWidth() / 2 && coordY > H - 100) {
                    commander.get(i).setPosition(center + i * Card.getCardsWidth() / 2, 0);
                }
            }
        }

    }

    public void centerTable() {
        if (!isPressed && unit.size() > 0) {
            W = Gdx.graphics.getWidth();
            H = Gdx.graphics.getHeight();
            int cot = 0; //cards on table
            int coordX = Gdx.input.getX();
            int coordY = Gdx.input.getY();
            for (int i = 0; i < unit.size(); i++) {
                if (unit.get(i).getState() == 3) {
                    cot++;
                }
            }
            int center = (W - (cot * Card.getCardsWidth() + (cot - 1) * 10)) / 2;
            int j = 0;
            for (int i = unit.size() - cot; i < unit.size(); i++) {
                if (unit.get(i).getState() == 3) {
                    unit.get(i).setPosition(center + j * Card.getCardsWidth() + j * 10, H / 2 - Card.getCardsHeight() - 20);
                    j++;
                }
            }
        }
    }

    public void draw() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.end();

        for (int i = 0; i < unit.size(); i++) {
            unit.get(i).draw(sr);
        }
        for (int i = 0; i < unit.size(); i++) {
            if (unit.get(i).getZoomed() == true) {
                unit.get(i).draw(sr);
            }
        }

        for (int i = 0; i < commander.size(); i++) {
            commander.get(i).draw(sr);
        }
        for (int i = 0; i < commander.size(); i++) {
            if (commander.get(i).getZoomed() == true) {
                commander.get(i).draw(sr);
            }
        }

    }

    public void dispose() {
        cardSounds[0].dispose();
        cardSounds[1].dispose();
        sb.dispose();
        sr.dispose();
        smallFont.dispose();
        standartFont.dispose();
        background.dispose();
        star.dispose();
        unit.clear();
        commander.clear();
    }

}
