package com.assemblewars.deckbuilding;

import com.assemblewars.filehandling.FileHandling;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class CountryListItem extends ListItems {


    public CountryListItem(int countryID) {
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
        countryName = FileHandling.readLine(countryID, "Database/CountryNames.txt");
        selected = false;
        this.countryID = countryID;

    }

    private void loadHeaders() {
        typeHeader[0] = new Texture(Gdx.files.internal("Graphics/Cards/Headers/header (" + 3 + ").png"));
        typeHeader[1] = new Texture(Gdx.files.internal("Graphics/Cards/Headers/header (" + 4 + ").png"));
    }  

    public void update() {

    }
    
    public String getCountryName(){
        return countryName;
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
    
    public int getCountryID(){
        return countryID;
    }


    public void draw() {
        sb.begin();
        standartFont.setColor(Color.BLACK);
        if (selected == true) {
            sb.draw(typeHeader[countryID%2], x - 5, y - 5, width + 10, height + 10);
            sb.draw(typeHeader[countryID%2], x, y, width, height);
            standartFont.draw(sb, countryName, x + 5, y + 20);
        }
        if (selected == false) {
            sb.draw(typeHeader[countryID%2], x, y, width, height);
            standartFont.draw(sb, countryName, x + 5, y + 20);
        }
        sb.end();
    }

}
