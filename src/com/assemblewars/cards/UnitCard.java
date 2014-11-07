package com.assemblewars.cards;

import com.assemblewars.filehandling.FileHandling;
import com.assemblewars.gamestates.PlayState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class UnitCard extends Card {

    private SpriteBatch sb;
    private BitmapFont smallFont;
    private BitmapFont standartFont;
    private BitmapFont indexFont;
    String countryName;
    int countryArea;

    public UnitCard(float x, float y, int ID) {

        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 10;
        smallFont = gen.generateFont(parameter);
        parameter.size = 20;
        standartFont = gen.generateFont(parameter);
        gen = new FreeTypeFontGenerator(Gdx.files.internal("indicators.ttf"));
        parameter.size = 12;
        indexFont = gen.generateFont(parameter);
        gen.dispose();

        this.x = x;
        this.y = y;
        cardID = ID;
        setAttributes();
        setState(1);
        setCountryAttributes();
        loadIndicators();
        cardTexture = new Texture(Gdx.files.internal("Graphics/Cards/CardTemplate.png"));
    }

    public void setCountryAttributes() {
        String attribute = FileHandling.readLine(getCountry(), "Database/Countries.txt");
        String[] attributes = new String[5];
        for (int i = 0; i < 5; i++) {
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
        flag = new Texture(Gdx.files.internal("Graphics/Cards/Flags/" + attributes[4] + ".png"));
    }

    public void setAttributes() {
        String attribute = FileHandling.readLine((cardID - 2000000), "Database/Cards/Units.txt");
        String[] attributes = new String[12];
        for (int i = 0; i < 12; i++) {
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
            setAttack(Integer.parseInt(attributes[8 + i]), 0 + i);
        }
        cardImage = new Texture(Gdx.files.internal("Graphics/Cards/unitImages/unitImage (" + attributes[11] + ").png"));
        typeHeader = new Texture(Gdx.files.internal("Graphics/Cards/Headers/header (" + getType() + ").png"));
    }

    public void update() {

    }

    public void draw(ShapeRenderer sr) {
        drawNormal(sr);
        if (getZoomed() == true) {
            drawZoomed(sr);
        }

    }

    public void drawNormal(ShapeRenderer sr) {
        //SHAPE
        sb.begin();
        sb.draw(indicator[3], getX(), getY() + height - 20);
        sb.draw(indicator[getType()], getX() + indicator[0].getWidth(), getY() + height - 20);
        sb.draw(indicator[getType()], getX() + 2 * indicator[0].getWidth(), getY() + height - 20);
        indexFont.setColor(Color.BLACK);
        String ats[] = new String[5];
        ats[0]=Integer.toString(getHealth());
        ats[1]=Integer.toString(getDefence(getType()));
        ats[2]=Integer.toString(getAttack(getType()));
        indexFont.draw(sb, ats[0], getX() + indicator[0].getWidth()/2 - (ats[0].length()*indexFont.getXHeight())/2, getY() + height + 10);
        indexFont.draw(sb, ats[1], getX() + 3*indicator[0].getWidth()/2 - (ats[1].length()*indexFont.getXHeight())/2, getY() + height + 10);
        indexFont.draw(sb, ats[2], getX() + 5*indicator[0].getWidth()/2 - (ats[2].length()*indexFont.getXHeight())/2, getY() + height + 10);
        for (int i = 0; i < 3; i++) {
            int s = 0;
            if (getDefence(i) > 0 && i != getType()) {
                ats[3]=Integer.toString(getDefence(i));
                sb.draw(indicator[i], getX() + 3 * indicator[i].getWidth(), getY() + height - 20);
                indexFont.draw(sb, ats[3], getX() + 7*indicator[0].getWidth()/2 - (ats[3].length()*indexFont.getXHeight())/2, getY() + height + 10);
                s=1;
            }
            if (getAttack(i) > 0 && i != getType()) {
                ats[4]=Integer.toString(getAttack(i));
                sb.draw(indicator[i], getX() + (3+s) * indicator[i].getWidth(), getY() + height - 20);
                indexFont.draw(sb, ats[4], getX() + (7+2*s)*indicator[0].getWidth()/2 - (ats[4].length()*indexFont.getXHeight())/2, getY() + height + 10);
            }
        }
        
        
        sb.draw(cardTexture, getX(), getY(), width, height);
        sb.draw(typeHeader, getX() + 7, getY() + 165,126,18);
        sb.draw(cardImage, getX() + 7, getY() + 92);
        sb.draw(flag, getX() + 7, getY() + 7,126,80);
        smallFont.setColor(Color.BLACK);
        smallFont.draw(sb, getName(), x + 8, y + height - 13);
        sb.end();
        sr.end();

    }

    public void drawZoomed(ShapeRenderer sr) {

        sb.begin();
        sb.draw(cardTexture, (PlayState.W / 2 - 2 * Card.getCardsWidth()), (PlayState.H / 2 - 2 * Card.getCardsHeight()), 4 * Card.getCardsWidth(), 4 * Card.getCardsHeight());
        sb.end();

        //SHAPE
        /*sr.setColor(Color.BLACK);
         sr.begin(ShapeType.Filled);
         sr.box((PlayState.W / 2 - 2 * Card.getCardsWidth()), (PlayState.H / 2 - 2 * Card.getCardsHeight()), 0, 4 * Card.getCardsWidth(), 4 * Card.getCardsHeight(), 0);
         sr.end();
         sr.setColor(Color.WHITE);
         sr.begin(ShapeType.Line);
         sr.box((PlayState.W / 2 - 2 * Card.getCardsWidth()), (PlayState.H / 2 - 2 * Card.getCardsHeight()), 0, 4 * Card.getCardsWidth(), 4 * Card.getCardsHeight(), 0);
         sr.end();*/
        //INFO
        int offset = 40;
        int centeringX = (PlayState.W / 2 - 2 * Card.getCardsWidth()) + 25;
        int centeringY = (PlayState.H / 2 + 2 * Card.getCardsHeight()) - 10;
        sb.begin();
        standartFont.setColor(Color.WHITE);
        standartFont.draw(sb, "UNIT CARD", centeringX, centeringY - 20);
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
            if (getAttack(i) != 0) {
                standartFont.draw(sb, "ATTACK: [" + getTypesName(i) + "]: " + getAttack(i), centeringX, centeringY - offset);
                offset += 10;
            }
        }
        sb.end();
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

    public void setAttack(int amount, int against) {
        attack[against] = amount;
    }

    public int getAttack(int against) {
        return attack[against];
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
}
