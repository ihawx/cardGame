package com.assemblewars.deckbuilding;

import com.assemblewars.filehandling.FileHandling;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardListItem extends ListItems {

    private int cardID;
    private int cardType;
    private String cardName;
    //
    //
    private int cardRole;
    private final int width = 410;
    private final int height = 26;

    public CardListItem(int cID, int ctype, int countID) {
        sb = new SpriteBatch();
        //
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        standartFont = gen.generateFont(parameter);
        gen.dispose();
        //
        loadHeaders();
        //
        cardID = cID;
        cardType = ctype;
        if (cID > 1000000 && cID < 2000000) {
            int linesCom = 0;

            try {
                linesCom = FileHandling.countLines("Database/Cards/Commanders.txt") + 1;
            } catch (IOException ex) {
                Logger.getLogger(CardListItem.class.getName()).log(Level.SEVERE, null, ex);
            }
            String attribute = new String();
            String[] attributes = new String[12];
            attribute = FileHandling.readLine(cardID - 1000001, "Database/Cards/Commanders.txt");
            attribute = attribute.replace("\t", "");
            attributes = attribute.split(";");
            cardName = attributes[1];
        }
        if (cID > 2000000 && cID < 3000000) {
            int linesUni = 0;
            try {
                linesUni = FileHandling.countLines("Database/Cards/Units.txt") + 1;
            } catch (IOException ex) {
                Logger.getLogger(CardListItem.class.getName()).log(Level.SEVERE, null, ex);
            }
            String attribute = new String();
            String[] attributes = new String[12];
            attribute = FileHandling.readLine(cardID - 2000001, "Database/Cards/Units.txt");
            attribute = attribute.replace("\t", "");
            attributes = attribute.split(";");
            cardName = attributes[1];
        }
        selected = false;

    }

    private void loadHeaders() {
        for (int i = 0; i < 4; i++) {
            typeHeader[i] = new Texture(Gdx.files.internal("Graphics/Cards/Headers/header (" + i + ").png"));
        }
    }

    public void update() {

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setSelection(boolean set) {
        selected = set;
    }

    public boolean getSelection() {
        return selected;
    }

    public int getCardID() {
        return cardID;
    }

    public void draw() {
        sb.begin();
        standartFont.setColor(Color.BLACK);
        if (selected == true) {
            sb.draw(typeHeader[cardType], x - 5, y - 5, width + 10, height + 10);
            sb.draw(typeHeader[cardType], x, y, width, height);
            standartFont.draw(sb, cardID + "   " + cardName, x + 5, y + 20);
        }
        if (selected == false) {
            sb.draw(typeHeader[cardType], x, y, width, height);
            standartFont.draw(sb, cardID + "   " + cardName, x + 5, y + 20);
        }
        sb.end();
    }

}
