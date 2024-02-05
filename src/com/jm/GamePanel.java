package com.jm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.DirectColorModel;

import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = 1L;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25; //how big do we want objects in this game
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; //how many objects we can have on the screen
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS]; //holds all the x components of the body part
    final int y[] = new int[GAME_UNITS]; //holds all the y components of the body part
    int bodyParts = 3;
    int applesEaten = 0;
    int appleX; //x co-ordinate of apple position
    int appleY; //y co-ordinate of apple position
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random = new Random();
    
    

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new myKeyAdapter());
        startGame();
    }

    public void startGame() {
        
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        draw(g);
        

    }
    

    public void draw(Graphics g) {
        
      
        if (running) {
          //draw lines across game panel to make a grid or matrix
//            for (int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
//                g.setColor(Color.red);
//                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//            }
            
            g.setColor(Color.GREEN);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            
            for(int i=0; i<bodyParts;i++) {
                if(i==0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    //multicolorSnake
                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            //score
            g.setColor(Color.RED);
            g.setFont(new Font("Times", Font.BOLD,35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        }
        else gameOver(g);
    } 
    
  //generate coordinate of new apples
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
        
        System.out.println(appleX+appleY);
    }

    public void move() {
        
        for(int i = bodyParts; i>0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        switch (direction) {
            case 'U': 
                y[0] = y[0] - UNIT_SIZE;
                break;
                
            case 'D': 
                y[0] = y[0] + UNIT_SIZE;
                break;
                
            case 'L': 
                x[0] = x[0] - UNIT_SIZE;
                break;
                
            case 'R': 
                x[0] = x[0] + UNIT_SIZE;
                break;
                    
            }
        }

    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollision() {
        //checks if head collides with bosy
        for (int i = bodyParts;i>0;i--) {
            if((x[0]== x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if (x[0]<0) {
            running = false;
        }
        
      //check if head touches right border
        if (x[0]>SCREEN_WIDTH) {
            running = false;
        }
        
      //check if head touches top border
        if (y[0]<0) {
            running = false;
        }
        
      //check if head touches bottom border
        if (y[0]>SCREEN_HEIGHT) {
            running = false;
        }
        
        if (!running) {
            timer.stop();
        }
        
    }

    public void gameOver(Graphics g) {
        //score
        g.setColor(Color.RED);
        g.setFont(new Font("Times", Font.BOLD,35));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
    
        //game over text
        g.setColor(Color.RED);
        g.setFont(new Font("Times", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Times", Font.BOLD, 25));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press Space to Replay", (SCREEN_WIDTH - metrics3.stringWidth("Press Space to Replay")) / 2, SCREEN_HEIGHT / 2 + 50);
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (running) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    public class myKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                    
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                    
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                    
                case KeyEvent.VK_SPACE:
                    if (!running) {
                        resetGame(); // Call the method to reset the game when space is pressed
                    }
                    break;
                }
        }
    }
    
    private void resetGame() {
        bodyParts = 3;
        applesEaten = 0;
        direction = 'R';
        running = true;
        for (int i = 0; i < bodyParts; i++) {
            x[i] = 0;
            y[i] = 0;
        }
        newApple();
        timer.restart(); // Restart the timer for the game loop
    }
}
