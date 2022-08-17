package org.MineSweeperGUI;

import javax.swing.*;

public class Game {
    public static void main(String[] args) {
        Runnable game = new org.MineSweeperGUI.MineSweeper.RunMineSweeper(); // Sets the game you want to run
        // here
        SwingUtilities.invokeLater(game);
    }
}
