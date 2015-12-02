package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class GameClient extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;

    private boolean running;

    public static int WIDTH = 800;
    public static int HEIGHT = 600;
    public static String NAME = "client";

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;


   // public static Sprite hero;

    private static int x = 0;
    private static int y = 0;
    ArrayList<ServerProcessor> s;
    ArrayList<Hero> heros = new ArrayList<>();
    Hero hero = new Hero(x,y);
    Client t1 = new Client(hero);

    public void start() {

        running = true;
        t1.start();
        new Thread(this).start();

    }

    public void run() {
        long lastTime = System.currentTimeMillis();
        long delta;

        init();

        while(running) {
            delta = System.currentTimeMillis() - lastTime;
            lastTime = System.currentTimeMillis();
            render();
            update(delta);
        }
    }

    public void init() {
        addKeyListener(new KeyInputHandler());
        hero.setSprite(getSprite("man.png"));


    }

    public void render() {



        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            requestFocus();
            return;
        }

        Graphics g = bs.getDrawGraphics();

       g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());


        hero.getSprite().draw(g, hero.getX(), hero.getY());

        g.dispose();
        bs.show();
    }
    public void collisionWall(int orient,int start_pos_x,int start_pos_y, int weight, int height){

        if(orient==1){
           if(x>weight ) {
            x = weight;
        }
        if(x<start_pos_x)x=start_pos_x;
        if(y>height ){
            y=height;
        }
        if(y<start_pos_y)y=start_pos_y;}

       }


    public void update(long delta) {
        collisionWall(1, 0, 0, WIDTH - 50, HEIGHT - 80);

        if (leftPressed) {
            x--;
            hero.setX(x);
        }
        if (rightPressed) {

            x++;
            hero.setX(x);
        }
        if (upPressed) {

            y--;
            hero.setY(y);
        }
        if (downPressed) {
            y++;
            hero.setY(y);
        }

    }

    public Sprite getSprite(String path) {
        BufferedImage sourceImage = null;

        try {
            URL url = this.getClass().getClassLoader().getResource(path);
            sourceImage = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Sprite sprite = new Sprite(Toolkit.getDefaultToolkit().createImage(sourceImage.getSource()));

        return sprite;
    }

    public static void main(String[] args) {
        GameClient game = new GameClient();
        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        JFrame frame = new JFrame(GameClient.NAME);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(game, BorderLayout.CENTER);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        game.start();

    }

    private class KeyInputHandler extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            }


            if (e.getKeyCode() == KeyEvent.VK_A ) {
                leftPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                downPressed = true;
            }

        }

        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_A) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_D) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_W) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_S) {
                downPressed = false;
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT ) {
                leftPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            }
        }
    }
}