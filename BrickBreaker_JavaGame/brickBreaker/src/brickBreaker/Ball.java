package brickBreaker;

import java.awt.*;

public class Ball {
    public int ballposX = 0;
    public int ballposY = 0;
    public int ballXdir = 0;
    public int ballYdir = 0;
    public boolean drawEnable =false;
    Rectangle ballRect = new Rectangle();


    public void putBallposX(int ballX){
        this.ballposX = ballX;
    }
    public void putBallposY(int ballY){
        this.ballposY = ballY;
    }
    public void putBalldirX(int dirX){
        this.ballXdir = dirX;
    }
    public void putBalldirY(int dirY){
        this.ballYdir = dirY;
    }
    public void putDrawEnable(boolean draw){
        this.drawEnable = draw;
    }

    public int getBallposX(){
        return ballposX;
    }
    public int getBallposY(){
        return ballposY;
    }
    public int getBalldirX(){
        return ballXdir;
    }
    public int getBalldirY(){
        return ballYdir;
    }

    public void putRect(){
        Rectangle ballRect2 = new Rectangle(this.ballposX, this.ballposY, 20,20);;
        this.ballRect =  ballRect2;
    }
    public Rectangle getrect(){
        return ballRect;
    }

    public boolean getDrawEnable(){
        return drawEnable;
    }

    public void changeDirX(){
        this.ballXdir =-ballXdir;
    }
    public void changeDirY(){
        this.ballYdir =-ballYdir;
    }

    public void ballMovementX(){
        this.ballposX += ballXdir;
    }

    public void ballMovementY(){
        this.ballposY += ballYdir;
    }

}

