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
public class Result {
    private Team team;
    private int score;

    public Result(Team team, int score) {
        this.team = team;
        this.score = score;
    }

    public Team getTeam() {
        return team;
    }

    public int getScore() {
        return score;
    }


    @Override
    public String toString() {
        return team.toString() + " " + score;
    }

}
