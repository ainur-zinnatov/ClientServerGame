package com.company;

/**
 * Created by hp on 01.12.2015.
 */
public class Hero {
    private ServerProcessor serverProcessor;
    private int x;
    private int y;
    private Sprite sprite;

    public Hero(ServerProcessor serverProcessor, int x, int y, Sprite sprite) {
        this.serverProcessor = serverProcessor;
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Hero(int y, int x, ServerProcessor serverProcessor) {
        this.y = y;
        this.x = x;
        this.serverProcessor = serverProcessor;
    }

    public Hero(int y, int x) {
        this.y = y;
        this.x = x;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public ServerProcessor getServerProcessor() {
        return serverProcessor;
    }

    public void setServerProcessor(ServerProcessor serverProcessor) {
        this.serverProcessor = serverProcessor;
    }
}
