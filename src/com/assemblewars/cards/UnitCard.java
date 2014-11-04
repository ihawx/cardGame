package com.assemblewars.cards;

import com.assemblewars.filehandling.FileHandling;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class UnitCard extends Card {

    private SpriteBatch sb;
    private BitmapFont font;
    String countryName;
    int countryArea;

    public UnitCard(float x, float y, int ID) {

        sb = new SpriteBatch();
        FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("SF.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 10;
        font = gen.generateFont(parameter);
        gen.dispose();

        this.x = x;
        this.y = y;
        cardID = ID;
        setAttributes();
        setState(0);
        countryName = FileHandling.readLine(getCountry(), "Database/Countries.txt");
        countryArea = Integer.parseInt(FileHandling.readLine(getCountry(), "Database/Countries_Areas_CA.txt"));

    }

    public void setAttributes() {
        String attribute = FileHandling.readLine((cardID - 2000000), "Database/Cards/Units.txt");
        String[] attributes = new String[11];
        for (int i = 0; i < 11; i++) {
            attributes[i] = "";
        }
        int k = 0;
        for (int i = 0; i < attribute.length(); i++) {
            if (!String.valueOf(attribute.charAt(i)).equals(";")) {
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

    }

    public void update() {
        
    }

    public void draw(ShapeRenderer sr) {
        //SHAPE
        sr.setColor(0, 0, 0, 1);
        sr.begin(ShapeType.Filled);
        sr.box(x, y, 0, width, height, 0);
        sr.end();
        sr.setColor(1, 1, 1, 1);
        sr.begin(ShapeType.Line);
        sr.box(x, y, 0, width, height, 0);
        sr.end();

        //TEXT        
        sb.begin();
        font.setColor(Color.ORANGE);
        font.draw(sb, "UNIT CARD", x + 5, y + height - 10);
        font.setColor(Color.GREEN);
        font.draw(sb, "CARD ID: " + Integer.toString(getCardID()), x + 5, y + height - 20);
        font.setColor(Color.RED);
        font.draw(sb, "NAME: " + getName(), x + 5, y + height - 30);
        font.setColor(Color.MAGENTA);
        font.draw(sb, "TYPE: " + getTypeName(), x + 5, y + height - 40);        
        font.setColor(Color.YELLOW);
        font.draw(sb, "COUNTRY ID: " + Integer.toString(getCountry()), x + 5, y + height - 50);
        font.setColor(Color.CYAN);
        font.draw(sb, "COUNTRY: " + getCountryName(), x + 5, y + height - 60);
        font.setColor(Color.RED);
        font.draw(sb, "AREA ID: " + Integer.toString(getCountryArea()), x + 5, y + height - 70);
        font.setColor(Color.GREEN);
        font.draw(sb, "AREA NAME: ", x + 5, y + height - 80);
        font.draw(sb, getAreaName(), x + 5, y + height - 90);
        font.setColor(Color.ORANGE);
        font.draw(sb, "HEALTH: "+getHealth(), x + 5, y + height - 100);
        int k=0;        
        for(int i =0; i<3; i++){            
            if(getDefence(i)!=0){
                font.setColor(Color.WHITE);
                font.draw(sb, "DEFENCE: ["+getTypesName(i)+"]: "+getDefence(i), x + 5, y + height - 110-k);
                k+=10;
            }
            if(getAttack(i)!=0){
                font.setColor(Color.YELLOW);
                font.draw(sb, "ATTACK: ["+getTypesName(i)+"]: "+getAttack(i), x + 5, y + height - 110-k);
                k+=10;
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
