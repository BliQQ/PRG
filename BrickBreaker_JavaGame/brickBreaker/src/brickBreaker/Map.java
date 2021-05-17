package brickBreaker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map{
    private int mapID=0;
    private int dimensionX=0;
    private int dimensionY=0;
    private int bricks=0;
    private int map[][];

    public FileInputStream inin = null;


    public void mapLoader(int ID) throws FileNotFoundException {
        int i=0,m=0,n=0,r=0,c=0;

        if(ID==1){
            inin = new FileInputStream("src\\brickBreaker\\1.txt");
        }
        if(ID==2){
            inin = new FileInputStream("src\\brickBreaker\\2.txt");
        }

        Scanner input  = new Scanner(inin);
        String rozmery = input.nextLine();

        mapID = ID;

        for (String s : rozmery.split(" ")) {
            if(i==0) {
                dimensionX = (Integer.parseInt(s));
            }
            if(i==1) {
                dimensionY = (Integer.parseInt(s));
            }
            bricks = dimensionX*dimensionY;
            i++;
        }
        map = new int[dimensionX][dimensionY];
        while(input.hasNext()){
            String x = input.next();
            map[r][c] = Character.getNumericValue(x.charAt(0));
            System.out.println(x.charAt(0));
            c++;
            if(c==m) {
                r++;
                c=0;
            }
        }
    }

    public int getDimensionX(){
        return dimensionX;
    }
    public int getDimensionY(){
        return dimensionY;
    }
    public int getMapID(){
        return mapID;
    }

}
