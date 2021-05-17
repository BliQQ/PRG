package brickBreaker;

import conectivity.ConnectionClass;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    ArrayList<Ball> Balls = new ArrayList<Ball>();

    private boolean play = false;
    private int score = 0;
    private int life = 3;
    private String name="";

    private int totalBricks = 0;

    private Timer timer;

    private int delay = 15;

    private int playerX = 310;

    private int heartXpos = 600;
    private int heartYpos = 20;

    private MapGenerator map;
    private Menu menu;
    private HighScore highScore;
    private HighScoreTable highScoreTable;

    private int lvl=1;

    Random random = new Random();

    boolean boostDraw = false;
    boolean newboost = false;

    final JFileChooser fileChooser = new JFileChooser();

    public int ammountofLVLs = 1;

    private enum STATE{
        MENU,
        GAME,
        SAVE,
        HIGHSCORE
    }
    private static STATE state;

    static {
        state = STATE.MENU;
    }

    public Gameplay() throws FileNotFoundException{
    Ball ball = new Ball();
    Balls.add(ball);
    ball.putDrawEnable(true);
    ball.putBalldirX(-1);
    ball.putBalldirY(-2);
    ball.putBallposX(350);
    ball.putBallposY(520);
    menu = new Menu();
    map = new MapGenerator(lvl);
    highScore = new HighScore();
    highScoreTable = new HighScoreTable();
    totalBricks = (int) MapGenerator.getdimensionValue();
    addKeyListener(this);
    setFocusable(true);
    setFocusTraversalKeysEnabled(false);
    timer = new Timer(delay, this);
    timer.start();
    }
    public void paint(Graphics g) {
      //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);

        if(state == STATE.SAVE){
            g.setColor(Color.yellow);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Nahral jsi " + score + " score", 200, 300);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Pro hrani znova zmakni enter", 200, 330);
            g.drawString("Nebo esc pro vypnutí hry", 200, 360);
            try {
                highScore.putScore(name,score);
                highScore.Sort_Score();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(state == STATE.GAME){
        //draw map

        map.draw((Graphics2D)g);

        //boarders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3, 592);
        g.fillRect(0,0,692, 3);
        g.fillRect(691,0,3, 592);

        // the_paddle
        g.setColor(Color.GREEN);
        g.fillRect(playerX, 550,100,8);

        // The_ball
        g.setColor(Color.yellow);

        g.fillOval(Balls.get(0).getBallposX(), Balls.get(0).getBallposY(),20,20);


        g.setColor(Color.white);
        g.drawString("Score :" + score, 20, 32);

        g.setColor(Color.white);
        g.drawString("Zbyva kostek :" + totalBricks, 100, 32);

        Font fnt0 = new Font("arial", Font.BOLD, 20);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("Uroveň : " + lvl, 290, 32);

        g.setColor(Color.YELLOW);

        switch(life){
            case 1 : {
                g.fillOval(heartXpos, heartYpos,20,20);
                break;
            }
            case 2 : {
                g.fillOval(heartXpos, heartYpos,20,20);
                g.fillOval(heartXpos+30, heartYpos,20,20);
                break;
            }
            case 3 : {
                g.fillOval(heartXpos, heartYpos,20,20);
                g.fillOval(heartXpos+30, heartYpos,20,20);
                g.fillOval(heartXpos+60, heartYpos,20,20);
                break;
            }
        }
        if(Balls.get(0).getBallposY() > 560) {
            play = false;
            Balls.get(0).putBalldirX(0);
            Balls.get(0).putBalldirY(0);

            if(life>0){
                g.setColor(Color.white);
                g.setFont(new Font("Serif", Font.BOLD, 30));
                g.drawString("Prisel jsi o zivot", 200, 300);
                g.setFont(new Font("Serif", Font.BOLD, 30));
                g.drawString("Pro pokracovani stiskni enter", 200, 330);
            }
            if(life==0){
                g.setFont(new Font("Serif", Font.BOLD, 30));
                g.drawString("Uz nemas dalsi zivoty", 200, 300);
                g.setFont(new Font("Serif", Font.BOLD, 30));
                g.drawString("GAME OVER", 200, 330);
            }
        }
        if(totalBricks==0){
            play = false;
            Balls.get(0).putBalldirX(0);
            Balls.get(0).putBalldirY(0);
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Uspesne jsi zvladl lvl", 200, 300);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Pro pokracovani stiskni enter", 200, 330);
        }
        }

        if(state == STATE.MENU){
            menu.renderMenu(g);
        }
        if(state == STATE.HIGHSCORE){
            highScoreTable.renderMenu(g);
        }
        g.dispose();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(state == STATE.GAME){
        timer.start();
        if(play) {
            if(new Rectangle( Balls.get(0).getBallposX(),  Balls.get(0).getBallposY(), 20, 20).intersects(new Rectangle(playerX,550,100,8))) {
                Balls.get(0).changeDirY();
            }
           A: for(int i = 0; i < map.map.length; i++){
                for (int j = 0; j < map.map[0].length; j++){
                    if(map.map[i][j] > 0){
                        int brickX = j*map.brickWidth + 80;
                        int brickY = i*map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Balls.forEach(Ball::putRect);
                        Rectangle brickRect = rect;

                            if(Balls.get(0).getrect().intersects(brickRect)) {
                                map.setBrickValue(1, i, j);
                                if(map.setBrickValue(0,i,j)==true) {
                                    score += 100;
                                    totalBricks--;
                                    int randomNum = random.nextInt(100);
                                    if(randomNum<20){
                                        boostDraw = true;
                                    }
                                }
                                if( Balls.get(0).getBallposX() + 19 <= brickRect.x ||  Balls.get(0).getBallposX() + 1 >= brickRect.x + brickRect.width ) {
                                    Balls.get(0).changeDirX();
                                }
                                else {
                                    Balls.get(0).changeDirY();
                                }
                                break A;
                            }
                       // }

                    }
                }
            }
            Balls.forEach(Ball::ballMovementY);
            Balls.forEach(Ball::ballMovementX);
            Balls.forEach(Ball::ballMovementY);
            Balls.forEach(Ball::ballMovementX);
            Balls.forEach(Ball::ballMovementY);
            Balls.forEach(Ball::ballMovementX);
            if( Balls.get(0).getBallposX() < 0) {
                Balls.get(0).changeDirX();
            }
            if(Balls.get(0).getBallposY() < 0) {
                Balls.get(0).changeDirY();
            }
            if(Balls.get(0).getBallposX() > 670) {
                Balls.get(0).changeDirX();
            }
        }
        repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(state == STATE.SAVE){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                state = STATE.GAME;
                play = true;
                Balls.get(0).putBallposX(350);
                Balls.get(0).putBallposY(520);
                Balls.get(0).putBalldirX(-1);
                Balls.get(0).putBalldirY(-2);
                playerX = 310;
                score = 0;
                life = 3;
                totalBricks = 63;
                try {
                    map = new MapGenerator(1);
                    totalBricks = MapGenerator.getdimensionValue();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
               System.exit(1);
            }
        }
        if(state == STATE.HIGHSCORE){
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                state = STATE.GAME;
                repaint();
            }
        }
        if(state == STATE.GAME){
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            }
            else {
                moveRight();
            }
    }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            if(!play && life == 0) {
                name = JOptionPane.showInputDialog("Please input mark for test 1: ");
                state=STATE.SAVE;

                repaint();
            }
            else if(!play && totalBricks==0){
                play = true;
                Balls.get(0).putBallposX(350);
                Balls.get(0).putBallposY(520);
                Balls.get(0).putBalldirX(-1);
                Balls.get(0).putBalldirY(-2);
                playerX = 310;
                if(lvl<ammountofLVLs){
                    lvl++;
                }
                else{
                    name = JOptionPane.showInputDialog("Please input mark for test 1: ");
                    state=STATE.SAVE;
                    lvl=1;
                    repaint();
                }
                try {
                    map = new MapGenerator(lvl);
                    totalBricks = MapGenerator.getdimensionValue();
                }
                catch (FileNotFoundException e1) {
                }
            }
             else if(!play){
                play = true;
                Balls.get(0).putBallposX(350);
                Balls.get(0).putBallposY(520);
                Balls.get(0).putBalldirX(-1);
                Balls.get(0).putBalldirY(-2);
                life--;
                repaint();
            }
        }
        }
        if(state == STATE.MENU){
            if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                menu.setChoseYDOWN(50);
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_UP) {
                menu.setChoseYUP(50);
                repaint();
            }
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                if(menu.getChoseY()==260){
                    state = STATE.GAME;
                    repaint();
                }
                if(menu.getChoseY()==310){
                    state = STATE.HIGHSCORE;
                    repaint();

                }
                if(menu.getChoseY()==360){
                    fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                    int result = fileChooser.showOpenDialog(null);
                }
                if(menu.getChoseY()==410){
                }
                if(menu.getChoseY()==460){
                    System.exit(0);
                }
            }

        }

}
    public void moveRight() {
        play = true;
        playerX += 40;
    }

    public void moveLeft() {
        play = true;
        playerX -= 40;
    }
}
