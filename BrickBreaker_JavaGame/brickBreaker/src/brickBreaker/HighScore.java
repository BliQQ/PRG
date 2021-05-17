package brickBreaker;

import conectivity.ConnectionClass;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class HighScore {
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

public void putScore(String name,int score) throws SQLException {
    Statement statement;
    String sql = "INSERT INTO userscore VALUES('"+name+"', "+score+");";
    statement = connection.createStatement();
    statement.executeUpdate(sql);
    System.out.println("data vlozena");
    }
    public ArrayList<HighScoreUnit> GetHSlist(){
    ConnectionClass connectionClass = new ConnectionClass();
    ArrayList<HighScoreUnit> HSlist = new ArrayList<HighScoreUnit>();

            String query = "SELECT * FROM userscore ";
            Statement st;
            ResultSet rs;
            try {
                st = connection.createStatement();
                rs = st.executeQuery(query);
                HighScoreUnit highscoreunit;
                while(rs.next()){
                    highscoreunit = new HighScoreUnit(rs.getString("Name"),rs.getInt("Score"));
                    HSlist.add(highscoreunit);System.out.println(HSlist.get(0).getName());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return HSlist;
        }

    public void Sort_Score(){
        ArrayList<HighScoreUnit> list = GetHSlist();
        Collections.sort(list, new Comparator<HighScoreUnit>() {
            @Override
            public int compare(HighScoreUnit o1, HighScoreUnit o2) {
                return Integer.valueOf(o2.Score).compareTo(o1.Score);
            }
        });
        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).getName()+list.get(i).getScore());
        }
    }


}

