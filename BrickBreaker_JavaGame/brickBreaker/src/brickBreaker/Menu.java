package brickBreaker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;

public class Menu implements KeyListener, ActionListener {
    Image img = null;
    public Rectangle playBztton = new Rectangle(180 + 120,180,100,50);
    public Rectangle settingsBztton = new Rectangle(180 + 120,280,100,50);
    public Rectangle exitBztton = new Rectangle(180 + 120,380,100,50);

    private int buttonX = 300;
    private int buttonY = 280;

    Timer tm = new Timer(5,this);
    private int choseX=275;
    private int choseY=260;

    public void renderMenu (Graphics g){
           try {
            img = ImageIO.read(new File("src\\brickBreaker\\background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //backgroud
          g.drawImage(img, 0, 0, null);

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("BRICK BREAKER",170,150);

        Font fnt1 = new Font("arial", Font.BOLD, 30);
        g.setFont(fnt1);
        g.drawString("PLAY", buttonX, buttonY);
        Font fnt2 = new Font("arial", Font.BOLD, 20);
        g.setFont(fnt2);
        g.drawString("HIGHSCORE", buttonX , buttonY + 50);
        g.setFont(fnt2);
        g.drawString("IMPORT", buttonX , buttonY + 100);
        g.setFont(fnt2);
        g.drawString("OPTIONS", buttonX , buttonY + 150);
        g.setFont(fnt1);
        g.drawString("QUIT", buttonX, buttonY + 200);
        g.setColor(Color.yellow);
        g.fillOval(choseX,choseY,20,20);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent q) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setChoseYUP(int k) {
        if(choseY-k>259){
            this.choseY = choseY-k;
        }
    }
    public void setChoseYDOWN(int k) {
        if(choseY+k<461){
            this.choseY = choseY+k;
        }

    }
    public int getChoseY() {
        return choseY;
    }
}
