package com.assemblewars.gamestates;

import com.assemblewars.cards.Card;
import com.assemblewars.cards.CommanderCard;
import com.assemblewars.cards.UnitCard;
import com.assemblewars.deckbuilding.CardListItem;
import com.assemblewars.deckbuilding.CountryListItem;
import com.assemblewars.filehandling.FileHandling;
import com.assemblewars.game.Screenshots;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeckBuildState extends GameState {

    private ArrayList<UnitCard> unit;
    private ArrayList<CommanderCard> commander;

    private ArrayList<CardListItem> items;
    private ArrayList<CountryListItem> countries;

    private SpriteBatch sb;
    private BitmapFont standartFont;
    private ShapeRenderer sr;

    private Texture typeHeader[] = new Texture[2];

    private static int W, H;
    private static int butW = 260;
    private static int butH = 26;

    private int[][] infos = new int[1000][3];
    int totalinfos;

    private final int MAX_ITEMS = 20;
    private static int selection = 0;
    private static int selectionCou = 0;

    String search = "";

    public DeckBuildState(GameStateManager gsm) {
        super(gsm);
        //
        items = new ArrayList<CardListItem>();
        countries = new ArrayList<CountryListItem>();
        unit = new ArrayList<UnitCard>();
        commander = new ArrayList<CommanderCard>();
        //
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        standartFont = gen.generateFont(parameter);
        gen.dispose();
        //
        sb = new SpriteBatch();
        sr = new ShapeRenderer();
        //
        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        //
        loadInformation();
        loadHeaders();
        init();
    }

    private void loadHeaders() {
        typeHeader[0] = new Texture(Gdx.files.internal("Graphics/buttonRedUP.png"));
        typeHeader[1] = new Texture(Gdx.files.internal("Graphics/buttonRedDOWN.png"));
    }

    public void loadInformation() {
        int linesCom = 0;
        int linesUni = 0;
        try {
            linesCom = FileHandling.countLines("Database/Cards/Commanders.txt") + 1;
            linesUni = FileHandling.countLines("Database/Cards/Units.txt") + 1;
        } catch (IOException ex) {
            Logger.getLogger(DeckBuildState.class.getName()).log(Level.SEVERE, null, ex);
        }
        String attribute[] = new String[Math.max(linesCom, linesUni)];
        String[][] attributes = new String[Math.max(linesCom, linesUni)][12];
        for (int i = 0; i < linesCom; i++) {
            attribute[i] = FileHandling.readLine(i, "Database/Cards/Commanders.txt");
            attribute[i] = attribute[i].replace("\t", "");
            attributes[i] = attribute[i].split(";");
            infos[i][0] = Integer.parseInt(attributes[i][0]);
            infos[i][1] = Integer.parseInt(attributes[i][3]);
            infos[i][2] = Integer.parseInt(attributes[i][2]);
        }
        for (int i = 0; i < linesUni; i++) {
            attribute[i] = FileHandling.readLine(i, "Database/Cards/Units.txt");
            attribute[i] = attribute[i].replace("\t", "");
            attributes[i] = attribute[i].split(";");
            infos[linesCom + i][0] = Integer.parseInt(attributes[i][0]);
            infos[linesCom + i][1] = Integer.parseInt(attributes[i][3]);
            infos[linesCom + i][2] = Integer.parseInt(attributes[i][2]);
        }
        do {
            totalinfos++;
        } while (infos[totalinfos][0] != 0);
        totalinfos--;

    }

    @Override
    public void init() {
        items.clear();
        loadCards();
        loadCountries();
    }

    private void loadCountries() {
        try {
            for (int i = 0; i < FileHandling.countLines("Database/CountryNames.txt") + 1; i++) {
                countries.add(new CountryListItem(i));
            }
        } catch (IOException ex) {
            Logger.getLogger(DeckBuildState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadCards() {
        int comlines = 0;
        int unlines = 0;
        try {
            comlines = FileHandling.countLines("Database/Cards/Commanders.txt") + 1;
            unlines = FileHandling.countLines("Database/Cards/Units.txt") + 1;
        } catch (IOException ex) {
            Logger.getLogger(DeckBuildState.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < comlines; i++) {
            items.add(new CardListItem(infos[i][0], infos[i][1], infos[i][2]));
        }
        for (int i = comlines; i < comlines + unlines; i++) {
            items.add(new CardListItem(infos[i][0], infos[i][1], infos[i][2]));
        }

    }

    @Override
    public void update() {

        W = Gdx.graphics.getWidth();
        H = Gdx.graphics.getHeight();
        //
        checkInput();
        //        

    }

    @Override
    public void draw() {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //
        sb.begin();
        standartFont.setColor(Color.WHITE);
        standartFont.draw(sb, search, W / 16, H - H / 25);
        sb.end();
        //
        int show = Math.min(MAX_ITEMS, items.size());
        for (int i = 0; i < show; i++) {
            items.get(i).setPosition(5 * W / 20, H - H / 14 - (i + 1) * H / 25);
            items.get(i).draw();
        }
        int show2 = Math.min(MAX_ITEMS, countries.size());
        for (int i = 0; i < show2; i++) {
            countries.get(i).setPosition(W / 20, H - H / 14 - (i + 1) * H / 25);
            countries.get(i).draw();
        }

        if (commander.size() > 0) {
            commander.get(0).drawZoomed(sr, W / 2 + W / 16, H / 16, Card.getCardsWidth() * 4, Card.getCardsHeight() * 4);
        }

        if (unit.size() > 0) {
            unit.get(0).drawZoomed(sr, W / 2 + W / 16, H / 16, Card.getCardsWidth() * 4, Card.getCardsHeight() * 4);
        }
        sb.begin();
        sb.draw(typeHeader[0], W / 20, H - H / 14, butW, butH);
        sb.draw(typeHeader[1], W / 20, H - H / 14 - (show2 + 1) * H / 25, butW, butH);
        sb.end();
    }

    @Override
    public void dispose() {
        sb.dispose();
        standartFont.dispose();
        sr.dispose();
        unit.clear();
        commander.clear();
        items.clear();
        countries.clear();
    }

    public void checkInput() {
        if (Gdx.input.isKeyJustPressed(Keys.BACKSPACE) && search.length() > 0) {
            search = search.substring(0, search.length() - 1);

        }

        Gdx.input.setInputProcessor(new InputProcessor() {

            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    selection = 1;
                    for (int i = 0; i < items.size(); i++) {
                        items.get(i).setSelection(false);
                    }
                    items.get(selection).setSelection(true);
                    Collections.rotate(items, -1);
                    Collections.sort(items, new Comparator<CardListItem>() {

                        public int compare(CardListItem o1, CardListItem o2) {
                            return Boolean.compare(o2.getSelection(), o1.getSelection());
                        }
                    });
                    return true;
                }
                if (keycode == Input.Keys.UP) {
                    selection = items.size() - 1;
                    for (int i = 0; i < items.size(); i++) {
                        items.get(i).setSelection(false);
                    }
                    items.get(selection).setSelection(true);
                    Collections.sort(items, new Comparator<CardListItem>() {

                        public int compare(CardListItem o1, CardListItem o2) {
                            return Boolean.compare(o2.getSelection(), o1.getSelection());
                        }
                    });
                    return true;
                }
                if (keycode == Input.Keys.ENTER && items.size() > 0) {
                    int cID = items.get(0).getCardID();
                    unit.clear();
                    commander.clear();
                    if (cID > 1000000 && cID < 2000000) {
                        commander.add(new CommanderCard(W / 2, H / 2, cID - 1));
                    }
                    if (cID > 2000000 && cID < 3000000) {
                        unit.add(new UnitCard(W / 2, H / 2, cID - 1));
                    }
                    return true;
                }
                if (keycode == Input.Keys.S) {
                    Screenshots.saveScreenshot();
                    return true;
                }
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                if (Character.isLetterOrDigit(character)) {
                    search += character;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (button == Input.Buttons.LEFT) {
                    if (screenX > W / 20 && screenX < W / 20 + butW && screenY < H / 14 && screenY > H / 14 - butH) {
                        selectionCou = countries.size() - 1;
                        items.clear();
                        for (int i = 0; i < countries.size(); i++) {
                            countries.get(i).setSelection(false);
                        }
                        countries.get(selectionCou).setSelection(true);
                        //Checking names
                        for(int i = 0; i<totalinfos; i++){
                            if(infos[i][2]==countries.get(selectionCou).getCountryID()){
                                items.add(new CardListItem(infos[i][0], infos[i][1], infos[i][2]));
                            }
                        }
                        //
                        Collections.sort(countries, new Comparator<CountryListItem>() {

                            public int compare(CountryListItem o1, CountryListItem o2) {
                                return Boolean.compare(o2.getSelection(), o1.getSelection());
                            }
                        });

                        return true;
                    }
                    if (screenX > W / 20 && screenX < W / 20 + butW && screenY < H / 14 + (20 + 1) * H / 25 && screenY > H / 14 + (20 + 1) * H / 25 - butH) {
                        selectionCou = 1;
                        items.clear();
                        for (int i = 0; i < countries.size(); i++) {
                            countries.get(i).setSelection(false);
                        }
                        countries.get(selectionCou).setSelection(true);
                        //Checking names
                        for(int i = 0; i<totalinfos; i++){
                            if(infos[i][2]==countries.get(selectionCou).getCountryID()){
                                items.add(new CardListItem(infos[i][0], infos[i][1], infos[i][2]));
                            }
                        }
                        //
                        Collections.rotate(countries, -1);
                        Collections.sort(countries, new Comparator<CountryListItem>() {

                            public int compare(CountryListItem o1, CountryListItem o2) {
                                return Boolean.compare(o2.getSelection(), o1.getSelection());
                            }
                        });
                        return true;
                    }
                }
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

}
