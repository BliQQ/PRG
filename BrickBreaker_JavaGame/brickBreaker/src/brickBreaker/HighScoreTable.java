package brickBreaker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HighScoreTable implements KeyListener, ActionListener {
    public HighScore highScore;

    public void renderMenu (Graphics g){
        g.setColor(Color.ORANGE);
        g.fillRect(1,1,692,592);

        highScore = new HighScore();

        ArrayList<HighScoreUnit> list = highScore.GetHSlist();
        Collections.sort(list, new Comparator<HighScoreUnit>() {
            @Override
            public int compare(HighScoreUnit o1, HighScoreUnit o2) {
                return Integer.valueOf(o2.Score).compareTo(o1.Score);
            }
        });




        Font fnt0 = new Font("arial", Font.BOLD, 20);
        g.setFont(fnt0);
        g.setColor(Color.WHITE);
        g.drawString("ORDER",50,50);
        g.drawString("NAME",250,50);
        g.drawString("SCORE",550,50);

        int i = 0;
        while(i<10){
            if(i==0){
                g.drawString(""+(1),50,80);
                g.drawString(""+list.get(i).getName(),250,80);
                g.drawString(""+list.get(i).getScore(),550,80);
            }
            else{
                g.drawString(""+(i),50,(40*i)+80);
                g.drawString(""+list.get(i).getName(),250,(40*i)+80);
                g.drawString(""+list.get(i).getScore(),550,(40*i)+80);
            }
            i++;
            if(i==list.size())break;

        }
        Font fnt1 = new Font("arial", Font.BOLD, 40);
        g.setFont(fnt1);
        g.setColor(Color.WHITE);
        g.drawString("PRESS ENTER: PLAY",50,500);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
 /*for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getName()+list.get(i).getScore());
        }*/