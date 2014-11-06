package com.assemblewars.cards;

import static com.assemblewars.cards.Card.width;
import com.assemblewars.filehandling.FileHandling;
import com.assemblewars.gamestates.PlayState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class CommanderCard extends Card {

    private SpriteBatch sb;
    private BitmapFont smallFont;
    private BitmapFont standartFont;
    String countryName;
    int countryArea;

    public CommanderCard(float x, float y, int ID) {

        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 10;
        smallFont = gen.generateFont(parameter);
        parameter.size = 20;
        standartFont = gen.generateFont(parameter);
        gen.dispose();

        this.x = x;
        this.y = y;
        cardID = ID;
        setAttributes();
        setState(3);
        setCountryAttributes();
    }

    public void setCountryAttributes() {
        String attribute = FileHandling.readLine(getCountry(), "Database/Countries.txt");
        String[] attributes = new String[4];
        for (int i = 0; i < 4; i++) {
            attributes[i] = "";
        }
        int k = 0;
        for (int i = 0; i < attribute.length(); i++) {
            if (!String.valueOf(attribute.charAt(i)).equals(";") && !String.valueOf(attribute.charAt(i)).equals("\t")) {
                attributes[k] += String.valueOf(attribute.charAt(i));
            }
            if (String.valueOf(attribute.charAt(i)).equals(";")) {
                k++;
            }
        }
        countryName = attributes[0];
        countryArea = Integer.parseInt(attributes[1]);
        Color1[getCountry()] = Color.valueOf(attributes[2]);
        Color2[getCountry()] = Color.valueOf(attributes[3]);
    }

    public void setAttributes() {
        String attribute = FileHandling.readLine((cardID - 1000000), "Database/Cards/Commanders.txt");
        String[] attributes = new String[8];
        for (int i = 0; i < 8; i++) {
            attributes[i] = "";
        }
        int k = 0;
        for (int i = 0; i < attribute.length(); i++) {
            if (!String.valueOf(attribute.charAt(i)).equals(";") && !String.valueOf(attribute.charAt(i)).equals("\t")) {
                attributes[k] += String.valueOf(attribute.charAt(i));
            }
            if (String.valueOf(attribute.charAt(i)).equals(";")) {
                k++;
            }
        }
        setName(attributes[1]);
        setCountry(Integer.parseInt(attributes[2]));
        setType(Integer.parseInt(attributes[3]));
        setHealth(Integer.parseInt(attributes[4]));
        for (int i = 0; i < 3; i++) {
            setDefence(Integer.parseInt(attributes[5 + i]), 0 + i);
        }

    }

    public void update() {

    }

    public void draw(ShapeRenderer sr) {
        drawNormal(sr);
        if (getZoomed() == true) {
            drawZoomed(sr);
        }
    }
    
    public void drawNormal(ShapeRenderer sr){
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.setColor(1, 1, 1, 1);
        sr.box(x, y, 0, width, height, 0);
        sr.setColor(0.4f, 0.4f, 0.4f, 1);
        sr.box(x + 3, y + 3, 0, width - 6, height - 6, 0);
        sr.end();
        
        sb.begin();
        smallFont.setColor(Color1[getCountry()]);
        smallFont.draw(sb, "COMMANDER", x + 5, y + height - 10);
        smallFont.setColor(Color2[getCountry()]);
        smallFont.draw(sb, Integer.toString(getCardID()), x + 5, y + height - 20);
        smallFont.setColor(Color1[getCountry()]);
        smallFont.draw(sb, getTypeName(), x + 5, y + height - 30);
        smallFont.setColor(Color2[getCountry()]);
        smallFont.draw(sb, getCountryName(), x + 5, y + height - 40);
        smallFont.setColor(Color1[getCountry()]);
        smallFont.draw(sb, getName(), x + 5, y + height - 50);
        sb.end();
    }
    
    public void drawZoomed(ShapeRenderer sr){
        sr.setColor(Color.BLACK);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.box((PlayState.W / 2 - 2 * Card.getCardsWidth()), (PlayState.H / 2 - 2 * Card.getCardsHeight()), 0, 4 * Card.getCardsWidth(), 4 * Card.getCardsHeight(), 0);
        sr.end();
        sr.setColor(Color.WHITE);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.box((PlayState.W / 2 - 2 * Card.getCardsWidth()), (PlayState.H / 2 - 2 * Card.getCardsHeight()), 0, 4 * Card.getCardsWidth(), 4 * Card.getCardsHeight(), 0);
        sr.end();
        
        int offset = 20;
        int centeringX = (PlayState.W / 2 - 2 * Card.getCardsWidth()) + 5;
        int centeringY = (PlayState.H / 2 + 2 * Card.getCardsHeight()) - 10;
        sb.begin();
        standartFont.setColor(Color.WHITE);
        standartFont.draw(sb, "COMMANDER", centeringX, centeringY);
        standartFont.draw(sb, "CARD ID: " + Integer.toString(getCardID()), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "NAME: " + getName(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "TYPE: " + getTypeName(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "COUNTRY ID: " + Integer.toString(getCountry()), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "COUNTRY: " + getCountryName(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "AREA ID: " + Integer.toString(getCountryArea()), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "AREA NAME: " + getAreaName(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.draw(sb, "STATE: " + getState(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.setColor(Color.RED);
        standartFont.draw(sb, "HEALTH: " + getHealth(), centeringX, centeringY - offset);
        offset += 20;
        standartFont.setColor(Color.TEAL);
        for (int i = 0; i < 3; i++) {
            if (getDefence(i) != 0) {
                standartFont.draw(sb, "DEFENCE: [" + getTypesName(i) + "]: " + getDefence(i), centeringX, centeringY - offset);
                offset += 20;
            }
        }
        sb.end();
    }

    public void setDefence(int amount, int against) {
        defence[against] = amount;
    }

    public int getDefence(int against) {
        return defence[against];
    }

    public void setHealth(int h) {
        health[getType()] = h;
    }

    public int getHealth() {
        return health[getType()];
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getCardID() {
        return cardID;
    }

    public void setType(int a) {
        for (int i = 0; i < type.length; i++) {
            type[i] = false;
        }
        if (a > 2) {
            a = 2;
            System.out.println("Invalid Card Type!");
            System.out.println("Seting Card Type To Naval");
        }
        type[a] = true;
    }

    public int getType() {
        int a = 0;
        for (int i = 0; i < type.length; i++) {
            if (type[i] == true) {
                a = i;
                break;
            }
        }
        return a;
    }

    public String getTypeName() {
        return typeNames[getType()];
    }

    public void setCountry(int a) {
        for (int i = 0; i < country.length; i++) {
            country[i] = 0;
        }
        if (a > country.length - 1) {
            a = country.length;
            System.out.println("Invalid Country ID!");
            System.out.println("Seting Country To " + country.length);
        }
        country[0] = a;
    }

    public int getCountry() {
        return country[0];
    }

    public String getCountryName() {
        return countryName;
    }

    public int getCountryArea() {
        return countryArea;
    }

    public String getAreaName() {
        return areaNames[getCountryArea() - 1];
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
