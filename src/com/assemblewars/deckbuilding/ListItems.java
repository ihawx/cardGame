
package com.assemblewars.deckbuilding;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class ListItems {
    protected String countryName;
    protected int countryID;
    protected boolean selected;
    //
    protected float x, y;
    protected final int width = 260;
    protected final int height = 26;
    //
    protected SpriteBatch sb;
    protected BitmapFont standartFont;
    protected Texture typeHeader[] = new Texture[4];
}
