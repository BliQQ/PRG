/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author beh01
 */
public class League {

    private final List<Game> games = new ArrayList<>();
    private final Set<Team> teams = new TreeSet<>();
    private final List<Result> result = new ArrayList<>();


    private League() {
    }
    private Team lookForTema(String teamName) {
        for (Team t : teams) {
            if (t.getName().equals(teamName)) {
                return t;
            }
        }
        Team result = new Team(teamName);
        teams.add(result);
        return result;
    }

    public List<Game> getGames() {

        return games;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void printResult() {
        System.out.printf("NHL Teams (%d):\n", teams.size());
        for (Team t : teams) {
            System.out.println(t);
        }
        System.out.println("Results:");
        for (Game game : games) {
            System.out.println(game);
        }
    }

    private static Result readResult(League league, Scanner s) {
        String team = "";
        String x = s.next();
        boolean first = true;
        while (!x.matches("[0-9]+")) {
            if (first) {
                first = false;
            } else {
                team += " ";
            }
            team += x;
            x = s.next();
        }
        return new Result(league.lookForTema(team), Integer.parseInt(x));
    }



    public static League loadData() {
        League league = new League();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:mm");

        try {
            Scanner input = new Scanner(new File("schedule.txt"));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                Scanner s = new Scanner(line);
                LocalDate date = LocalDate.parse(s.next(), dateFormat);
                Result home = readResult(league, s);
                Result visit = readResult(league, s);
                String nextString = s.next();
                Note note = null;
                if (nextString.equals("OT")) {
                    note = Note.OVERTIME_VICTORY;
                    nextString = s.next();
                }
                if (nextString.equals("SO")) {
                    note = Note.SHOOTOUT;
                    nextString = s.next();
                }
                nextString = nextString.replace(",", "");
                int numberOfvisitors = Integer.parseInt(nextString);
                LocalTime l = LocalTime.parse(s.next(),timeFormat);
                league.getGames().add(new Game(date, home, visit, numberOfvisitors, l, note));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return league;
    }


    public void getWinStreak(){
        for (Game game : games) {
            int scoreHome = game.getHomeTeam().getScore();
            int scoreVisit = game.getVisitors().getScore();

            if(scoreHome>scoreVisit){
                String stringWinTeam = game.getHomeTeam().getTeam().toString();
                String stringLoseTeam = game.getVisitors().getTeam().toString();
                for (Team t : teams) {
                    if(t.getName().equals(stringWinTeam)){
                        t.winStreakPlus();
                    }
                    if(t.getName().equals(stringLoseTeam)){
                        t.winStreakReset();
                    }
                }
            }

            if(scoreHome<scoreVisit){
                String stringWinTeam = game.getVisitors().getTeam().toString();
                String stringLoseTeam = game.getHomeTeam().getTeam().toString();
                for (Team t : teams) {
                    if(t.getName().equals(stringWinTeam)){
                        t.winStreakPlus();
                    }
                    if(t.getName().equals(stringLoseTeam)){
                        t.winStreakReset();
                    }
                }
            }



        }


    }


    public void getScore(){
        for (Game game : games) {
            int scoreHome = game.getHomeTeam().getScore();
            int scoreVisit = game.getVisitors().getScore();
            Note note = game.getNote();

            if (note == Note.SHOOTOUT || note == Note.OVERTIME_VICTORY){
                if(scoreHome>scoreVisit){
                String stringWinTeam = game.getHomeTeam().getTeam().toString();
                String stringLoseTeam = game.getVisitors().getTeam().toString();
                for (Team t : teams) {
                    if(t.getName().equals(stringWinTeam)){
                        t.winInOvertime();
                    }
                    if(t.getName().equals(stringLoseTeam)){
                        t.lostInOvertime();
                    }
                }
            }
                 else{
                     String stringWinTeam = game.getVisitors().getTeam().toString();
                     String stringLoseTeam = game.getHomeTeam().getTeam().toString();
                    for (Team t : teams) {
                        if(t.getName().equals(stringWinTeam)){
                            t.winInOvertime();
                        }
                        if(t.getName().equals(stringLoseTeam)){
                            t.lostInOvertime();
                        }
                    }

                }
            }
            if(scoreVisit>scoreHome && note != Note.SHOOTOUT && note != Note.OVERTIME_VICTORY){
                String stringWinTeam = game.getVisitors().getTeam().toString();
                for (Team t : teams) {
                    if(t.getName().equals(stringWinTeam)){
                        t.win();
                    }
                }
        }
            if(scoreHome>scoreVisit && note != Note.SHOOTOUT && note != Note.OVERTIME_VICTORY){
                String stringWinTeam = game.getHomeTeam().getTeam().toString();
                for (Team t : teams) {
                    if(t.getName().equals(stringWinTeam)){
                        t.win();
                    }
                }
            }
    }
}
    public void lolo() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("tabilka.txt"));
        for (Team t : teams) {
            writer.write(t.getName()+ "    ");
            writer.write(String.valueOf(t.getScore()));
            writer.newLine();
        }
        writer.close();
    }
    public void writeWinStreak() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("winstreak tab.txt"));
        for (Team t : teams) {
            writer.write(t.getName()+ "    ");
            writer.write(String.valueOf(t.getWinStreak()));
            writer.newLine();
        }
        writer.close();
    }

}


/*public class JavaApplication2 {


public static void main(String[] args) {
    ArrayList<mesto> mesta = new ArrayList<mesto>();
    Scanner scan = new Scanner(System.in);
    String nameMesto,nameMesto2;
    int pocet_mest,pocet_cest;

    pocet_mest = scan.nextInt();

    for(int i = 0; i <= pocet_mest;i++){
        mesto newMesto = new mesto();
        mesta.add(newMesto);
        nameMesto = scan.nextLine();
        newMesto.setName(nameMesto);
    }

    pocet_cest = scan.nextInt();

    for(int i = 0; i <= ((pocet_cest)/2);i++){
        nameMesto = scan.next();
        nameMesto2 = scan.next();

        for(int j = 0; j <= pocet_mest;j++){
            if(nameMesto.equals(mesta.get(j).name)) {
                mesta.get(j).setRoad(nameMesto2);
            }
        }
        for(int j = 0; j <= pocet_mest;j++){
            if(nameMesto2.equals(mesta.get(j).name)) {
                mesta.get(j).setRoad(nameMesto);
            }
        }
    }

    for(int i = 0; i <= pocet_mest;i++){
        System.out.println(mesta.get(i).name);
        System.out.println(mesta.get(i).roads);

    }
    */