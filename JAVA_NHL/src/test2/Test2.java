/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test2;

import java.io.IOException;

/**
 *
 * @author beh01
 */
public class Test2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        League league = League.loadData();
        //league.printResult();
        league.getScore();
        league.getWinStreak();
        league.writeWinStreak();
        //league.lolo();
    }
}
