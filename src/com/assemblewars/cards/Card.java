package com.assemblewars.cards;

public abstract class Card {

    protected int cardID; //ID of the card in the files
    
    protected int playerID; //owner ofthe card
    
    protected String name; //card's name
    
    protected int state; //where the card is located
    //0 - deck
    //1 - hand
    //2 - table
    //3 - graveyard

    protected boolean[] type = new boolean[3]; // type of the card
    protected final int AIR = 0;
    protected final int LAND = 1;
    protected final int NAVAL = 2;
    protected static String [] typeNames = {"AIR","LAND","NAVAL"};

    protected static final int NUMBER_OF_COUNTRIES = 195;
    protected int[] country = new int[NUMBER_OF_COUNTRIES];
    protected int[] area;
    protected int[] organistation;
    
    protected String [] areaNames = {"NORTHERN AMERICA","CENTRAL AMERICA","CARRIBEAN","LATIN AMERICA","LA PLATA","WESTERN EUROPE",
                                    "SOUTHERN EUROPE","CENTRAL EUROPE","BALKANS","EASTERN EUROPE","SCANDINAVIA","ASIA MINOR",
                                    "MIDDLE EAST","CENTRAL ASIA","SOUTHERN ASIA","EAST INDIES","OCEANIA","EASTERN ASIA",
                                    "NORTHERN AFRICA","GOLDEN COAST","CENTRAL AFRICA","EASTERN AFRICA","SOUTHERN AFRICA"};

    protected int[] health = new int[3];
    protected int[] defence = new int[3];
    protected int[] attack = new int[3];
    
    protected boolean zoom;

    protected static int width = 150; //width of the card
    protected static int height = 200; //height of the card

    protected float x; //x coord of the lower left corner
    protected float y; //z coord of the lower left corner
    
    protected void setState(int state){
        this.state = state;
    }
    
    protected int getState(){
        return state;
    }
    
    public boolean getZoomed(){
        return zoom;
    }
    
    public void setZoom(boolean set){
        zoom=set;
    }
    
    public static int getCardsWidth(){
        return width;
    }
    
    public static int getCardsHeight(){
        return height;
    }

    public static int getNumberOfCountries() {
        return NUMBER_OF_COUNTRIES;
    }
    
    public static String getTypesName(int a){
        return typeNames[a];
    }

}
