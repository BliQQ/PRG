/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author beh01
 */
public class Game {
    
    private final LocalDate date;
    private final Result homeTeam;
    private final Result visitors;
    private final int numberOfvisitors;
    private final LocalTime gameLength;
    private final Note note;
    
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("H:m");
    
    public Game(LocalDate date, Result homeTeam, Result visitors, int numberOfvisitors, LocalTime gameLength, Note note) {
        this.date = date;
        this.homeTeam = homeTeam;
        this.visitors = visitors;
        this.numberOfvisitors = numberOfvisitors;
        this.gameLength = gameLength;
        this.note = note;

    }
    
    public Result getHomeTeam() {
        return homeTeam;
    }

    public Result getVisitors() {
        return visitors;
    }

    public int getNumberOfvisitors() {
        return numberOfvisitors;
    }
    
    public LocalTime getGameLength() {
        return gameLength;
    }
    
    public Note getNote() {
        return note;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    @Override
    public String toString() {
        String note = "";
        if (this.note == Note.OVERTIME_VICTORY) {
            note += " OT";
        }
        if (this.note == Note.SHOOTOUT) {
            note += " SO";
        }
        return dateFormat.format(date) + " - " + homeTeam + " x " + visitors + " (" + numberOfvisitors + ", " + timeFormat.format(gameLength) + note + ")";
    }
    
}
