package brickBreaker;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class MapGenerator{
    private static int dimensionValue=0;

    public int map[][];
    public int brickWidth;
    public int brickHeight;
    public  FileInputStream in = null;
    public int space=0;
    public MapGenerator(int ID) throws FileNotFoundException {
        int i = 0,m=0,n=0,r=0,c=0;

        try{
            in = new FileInputStream("src\\brickBreaker\\"+ID+".txt");
        }
        catch(IOException e){
        }

        Scanner input  = new Scanner(in);
        String rozmery = input.nextLine();
        for (String s : rozmery.split(" ")) {
            if(i==0) {
                m = (Integer.parseInt(s));
            }
            if(i==1) {
                n = (Integer.parseInt(s));
            }
            dimensionValue = m*n;
            i++;
        }
        map = new int[m][n];
        while(input.hasNext()){
            String x = input.next();
            map[r][c] = Character.getNumericValue(x.charAt(0));
            if ( map[r][c] == 0){
                space++;
            }
            c++;
            if(c==m) {
                r++;
                c=0;
            }
        }
        dimensionValue -= space;
        brickWidth = 540/m;
        brickHeight = 150/n;
    }
    public void draw(Graphics2D g) {
        for(int i = 0; i < map.length;i++) {
            for(int j=0; j < map[0].length; j++){
                if(map[i][j] > 0) {
                    switch(map[i][j]){
                        case 1 : {
                            g.setColor(Color.white);
                            g.fillRect(j * brickWidth + 80, i * brickHeight +50, brickWidth, brickHeight);
                            break;
                        }
                        case 2 : {
                            g.setColor(Color.green);
                            g.fillRect(j * brickWidth + 80, i * brickHeight +50, brickWidth, brickHeight);
                            break;
                        }
                        case 3 : {
                            g.setColor(Color.blue);
                            g.fillRect(j * brickWidth + 80, i * brickHeight +50, brickWidth, brickHeight);
                            break;
                        }
                        case 4 : {
                            g.setColor(Color.PINK);
                            g.fillRect(j * brickWidth + 80, i * brickHeight +50, brickWidth, brickHeight);
                            break;
                        }
                    }
                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.BLACK);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    public boolean setBrickValue(int value, int row, int col) {
        map[row][col] -= value;
        if(map[row][col]==0) {
            return true;
        }
        else {
            return false;
        }
    }
    public static int getdimensionValue(){
        return dimensionValue;
    }
  /* public void getFile(File inpo){
       this.userIn = inpo;
   }
*/
}
