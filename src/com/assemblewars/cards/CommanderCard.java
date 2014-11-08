package com.assemblewars.cards;

import static com.assemblewars.cards.Card.width;
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

public class CommanderCard extends Card {

    private SpriteBatch sb;
    private BitmapFont smallFont;
    private BitmapFont standartFont;
    private BitmapFont indexFont;
    String countryName;
    int countryArea;

    public CommanderCard(float x, float y, int ID) {

        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 10;
        smallFont = gen.generateFont(parameter);
        gen = new FreeTypeFontGenerator(Gdx.files.internal("indicators.ttf"));
        parameter.size = 27;
        standartFont = gen.generateFont(parameter);
        gen = new FreeTypeFontGenerator(Gdx.files.internal("indicators.ttf"));
        parameter.size = 12;
        indexFont = gen.generateFont(parameter);
        gen.dispose();

        cardTexture = new Texture(Gdx.files.internal("Graphics/Cards/CardTemplate.png"));
        zoomedTemplate = new Texture(Gdx.files.internal("Graphics/Cards/CardTemplate3.png"));

        this.x = x;
        this.y = y;
        cardID = ID;
        setAttributes();
        setState(3);
        setCountryAttributes();
        loadIndicators();
    }

    public void setCountryAttributes() {
        String attribute = FileHandling.readLine(getCountry(), "Database/Countries.txt");
        String[] attributes = new String[FileHandling.countChars(';', attribute)];
        attribute = attribute.replace("\t", "");
        attributes = attribute.split(";");
        countryName = attributes[0];
        countryArea = Integer.parseInt(attributes[1]);
        Color1[getCountry()] = Color.valueOf(attributes[2]);
        Color2[getCountry()] = Color.valueOf(attributes[3]);
        flag = new Texture(Gdx.files.internal("Graphics/Cards/Flags/" + attributes[4] + ".png"));

    }

    public void setAttributes() {
        String attribute = FileHandling.readLine((cardID - 1000000), "Database/Cards/Commanders.txt");
        String[] attributes = new String[FileHandling.countChars(';', attribute)];
        attribute = attribute.replace("\t", "");
        attributes = attribute.split(";");
        setName(attributes[1]);
        setCountry(Integer.parseInt(attributes[2]));
        setType(Integer.parseInt(attributes[3]));
        setHealth(Integer.parseInt(attributes[4]));
        for (int i = 0; i < 3; i++) {
            setDefence(Integer.parseInt(attributes[5 + i]), 0 + i);
        }
        cardImage = new Texture(Gdx.files.internal("Graphics/Cards/unitImages/unitImage (" + attributes[8] + ").png"));
        typeHeader = new Texture(Gdx.files.internal("Graphics/Cards/Headers/header (" + getType() + ").png"));
    }

    public void update() {

    }

    public void draw(ShapeRenderer sr) {
        drawNormal(sr);
        if (getZoomed() == true) {
            int W = Gdx.graphics.getWidth();
            int H = Gdx.graphics.getHeight();
            drawZoomed(sr, W / 2 - Card.getCardsWidth() * 2, 10, Card.getCardsWidth() * 4, Card.getCardsHeight() * 4);
        }
    }

    public void drawNormal(ShapeRenderer sr) {
        sb.begin();
        sb.draw(indicator[3], getX(), getY() + height - 20);
        sb.draw(indicator[getType()], getX() + indicator[0].getWidth(), getY() + height - 20);
        indexFont.setColor(Color.BLACK);
        String ats[] = new String[2];
        ats[0] = Integer.toString(getHealth());
        ats[1] = Integer.toString(getDefence(getType()));
        indexFont.draw(sb, ats[0], getX() + indicator[0].getWidth() / 2 - (ats[0].length() * indexFont.getXHeight()) / 2, getY() + height + 10);
        indexFont.draw(sb, ats[1], getX() + 3 * indicator[0].getWidth() / 2 - (ats[1].length() * indexFont.getXHeight()) / 2, getY() + height + 10);

        sb.draw(cardTexture, getX(), getY(), width, height);
        sb.draw(typeHeader, getX() + 7, getY() + 165, 126, 18);
        sb.draw(cardImage, getX() + 7, getY() + 92);
        sb.draw(flag, getX() + 7, getY() + 7, 126, 80);
        smallFont.setColor(Color.BLACK);
        smallFont.draw(sb, getName(), x + 8, y + height - 13);
        sb.end();
        sr.end();

    }

    public void drawZoomed(ShapeRenderer sr, float coordX, float coordY, float wid, float hei) {

        sb.begin();
        float scaleX = (wid / Card.getCardsWidth());
        float scaleY = (hei / Card.getCardsHeight());
        //Card Template
        sb.draw(zoomedTemplate, coordX, coordY, wid, hei);
        //Header + Name
        sb.draw(typeHeader, coordX + 7 * scaleX, coordY + hei - 25 * scaleY, wid - 14 * scaleX, 18 * scaleY);
        standartFont.setColor(Color.BLACK);
        standartFont.draw(sb, name, coordX + 7 * scaleX + 10, coordY + hei - 25 * scaleY + 47);
        //Photo + Flag
        sb.draw(cardImage, coordX + 7 * scaleX, coordY + hei - 62 * scaleY, wid - 80 * scaleX, 33 * scaleY);
        sb.draw(flag, coordX + 73 * scaleX, coordY + hei - 62 * scaleY, wid - 80 * scaleX, 33 * scaleY);
        //Health
        sb.draw(zoomedHeaders[1], coordX + 7 * scaleX, coordY + hei - 98 * scaleY, wid - 80 * scaleX, 31 * scaleY);
        sb.draw(zoomedIcons[0], coordX + 7 * scaleX + 15, coordY + hei - 90 * scaleY, 15 * scaleY, 15 * scaleY);
        standartFont.draw(sb, Integer.toString(getHealth()), coordX + 7 * scaleX + 20 + 15 * scaleY, coordY + hei - 80 * scaleY);
        //Defence
        sb.draw(typeHeader, coordX + 73 * scaleX, coordY + hei - 98 * scaleY, wid - 80 * scaleX, 31 * scaleY);
        sb.draw(zoomedIcons[1], coordX + 73 * scaleX + 15, coordY + hei - 90 * scaleY, 15 * scaleY, 15 * scaleY);
        standartFont.draw(sb, Integer.toString(getDefence(getType())), coordX + 73 * scaleX + 20 + 15 * scaleY, coordY + hei - 80 * scaleY);
        //Card ID        
        sb.draw(zoomedHeaders[0], coordX + 7 * scaleX, coordY + hei - 122 * scaleY, wid - 14 * scaleX, 18 * scaleY);
        standartFont.draw(sb, "Card ID: " + Integer.toString(cardID), coordX + 7 * scaleX + 10, coordY + hei - 122 * scaleY + 47);
        //Country Name
        sb.draw(zoomedHeaders[1], coordX + 7 * scaleX, coordY + hei - 142 * scaleY, wid - 14 * scaleX, 18 * scaleY);
        standartFont.draw(sb, getCountryName(), coordX + 7 * scaleX + 10, coordY + hei - 142 * scaleY + 47);
        //Area Name
        sb.draw(zoomedHeaders[0], coordX + 7 * scaleX, coordY + hei - 162 * scaleY, wid - 14 * scaleX, 18 * scaleY);
        standartFont.draw(sb, getAreaName(), coordX + 7 * scaleX + 10, coordY + hei - 162 * scaleY + 47);
        //Organisations
        sb.draw(zoomedHeaders[1], coordX + 7 * scaleX, coordY + hei - 182 * scaleY, wid - 14 * scaleX, 18 * scaleY);
        standartFont.draw(sb, "Oganisations: TBD ", coordX + 7 * scaleX + 10, coordY + hei - 182 * scaleY + 47);
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
