package brickBreaker;

import javafx.scene.control.TextInputDialog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {
        JFrame obj = new JFrame();
        int GameWidth = 700;
        int GameHeight = 600;
        Gameplay gamePlay = new Gameplay();
        obj.setBounds(10, 10, GameWidth,GameHeight);

        obj.setTitle("Breakout Ball");
        obj.setResizable(false);

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        obj.add(gamePlay);
        obj.setVisible(true);



    }
}
