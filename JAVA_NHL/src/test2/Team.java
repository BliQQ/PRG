/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2;

/**
 *
 * @author beh01
 */
public class Team implements Comparable<Team> {

    private String name;
    private int score;
    private int winStreak;


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


    public Team(String name) {
        this.name = name;
    }

    public void win(){
        this.score += 3;
    }
    public void winInOvertime(){
        this.score += 2;
    }
    public void lostInOvertime(){
        this.score += 1;
    }

    public void winStreakPlus(){
        this.winStreak += 1;
    }
    public void winStreakReset(){
        this.winStreak = 1;
    }

    public int getWinStreak(){
        return this.winStreak;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Team o) {
        return this.getName().compareTo(o.getName());
    }


}
