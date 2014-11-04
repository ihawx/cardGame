package com.assemblewars.game;

import com.assemblewars.cards.Card;
import com.assemblewars.cards.UnitCard;
import com.assemblewars.gamestates.GameStateManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGameClass extends ApplicationAdapter {

    private ArrayList<UnitCard> unit;
    private GameStateManager gsm;
    ShapeRenderer sr;
    float i = 0f;
    int k = 0;
    int a = 0, b = 0, kk = 0;
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

        sr = new ShapeRenderer();
        unit = new ArrayList<UnitCard>();

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
        //listCountries(false);
    }

    public void listCountries(boolean with) {
        FileHandle file = Gdx.files.internal("Database\\Countries.txt");;
        if (with == true) {
            file = Gdx.files.internal("Database\\Countries_Areas.txt");
        }
        if (with == false) {
            file = Gdx.files.internal("Database\\Countries.txt");
        }
        BufferedReader reader = new BufferedReader(file.reader());
        List<String> lines = new ArrayList<String>();
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(MainGameClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (line != null) {
            lines.add(line);
            try {
                line = reader.readLine();
            } catch (IOException ex) {
                Logger.getLogger(MainGameClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        sb.begin();
        font.setColor(Color.WHITE);
        int W = Gdx.graphics.getWidth();
        int H = Gdx.graphics.getHeight();
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).matches("[0-9]+")) {
                font.setColor(Color.RED);
            } else {
                font.setColor(Color.WHITE);
            }
            if (i <= 48) {
                font.draw(sb, i + ": " + lines.get(i), 10, H - 17 * i);
            }
            if (i > 48 && i <= 96) {
                font.draw(sb, i + ": " + lines.get(i), 370, H - 17 * i + 48 * 17);
            }
            if (i > 96 && i <= 144) {
                font.draw(sb, i + ": " + lines.get(i), 660, H - 17 * i + 2 * 48 * 17);
            }
            if (i > 144 && i <= 192) {
                font.draw(sb, i + ": " + lines.get(i), 960, H - 17 * i + 3 * 48 * 17);
            }
            if (i > 192) {
                font.draw(sb, i + ": " + lines.get(i), 1230, H - 17 * i + 4 * 48 * 17);
            }
        }
        sb.end();
    }
}
