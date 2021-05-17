package brickBreaker;

public class HighScoreUnit {
    public String Name ="";
    public int Score=0;

    public HighScoreUnit(String name, int score) {
        this.Name = name;
        this.Score = score;
    }


    public String getName(){
        return Name;
    }
    public int getScore(){
        return Score;
    }
}


