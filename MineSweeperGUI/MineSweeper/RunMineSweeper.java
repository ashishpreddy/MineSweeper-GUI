package org.MineSweeperGUI.MineSweeper;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * <p>
 * This game adheres to a Model-View-Controller design framework.
 * <p>
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a MineSweeper object to serve as the game's model.
 */
public class RunMineSweeper implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("MineSweeper");
        frame.setLocation(450, 150);

        // Checks if there is a previously unfinished game
        File f = new File("moves.csv");
        boolean exists = f.exists();
        boolean loadPrevious = false;
        if (exists) {
            JOptionPane pane = new JOptionPane(
                    "A previous game was detected. Would you like to load the previous game?"
            );
            Object[] options = new String[] { "No", "Yes" };
            pane.setOptions(options);
            JDialog dialog = pane.createDialog(new JFrame(), "MineSweeper: Load Previous Game?");
            dialog.setVisible(true);
            Object obj = pane.getValue();
            int result = -1;
            for (int k = 0; k < options.length; k++) {
                if (options[k].equals(obj)) {
                    result = k;
                }
            }
            if (result == 1) {
                loadPrevious = true;
            }
        }

        // Frame for Instructions
        JFrame instructionsPane = new JFrame("MineSweeper Instructions");
        instructionsPane.setSize(475, 480);
        instructionsPane.setLocation(466, 200);

        JEditorPane instructionsText = new JEditorPane();
        instructionsText.setEditable(false);
        instructionsText.setContentType("text/html");
        String text = "<h1 style=\"text-align:center\">MineSweeper Instructions</h1>\n" +
                "<h2 style=\"text-align:center\">Selecting a Cell</h2>\n" +
                "<p>Click a cell at random to start the game. The cells that have a mine number" +
                " indicate the number of mines directly surrounding the cell (there are 8 total" +
                " cells that surround each cell). Based on these values, open empty cells " +
                "strategically to not hit a mine. If you hit a mine, you lose the game.</p>\n" +
                "<h2 style=\"text-align:center\">Flagging a Cell</h2>\n<p> Flag a cell if you" +
                " suspect that it is a mine. You can flag by right clicking on the cell.</p>\n" +
                "<h2 style=\"text-align:center\">Winning the Game</h2>\n<p>You win the game by " +
                "opening all the empty cells and not touching any of the mines. You do not need" +
                " to have mine cells flagged in order to win. There are between 12 to 15 mines" +
                " to find!</p>\n<h2 style=\"text-align:center\">Misc. Notes</h2>\n" +
                "<p>You can't flag an opened cell. You can't open a flagged cell.</p>\n" +
                "<h2 style=\"text-align:center\">Good luck!!</h2>\n" +
                "\n";
        instructionsText.setText(text);
        instructionsPane.setContentPane(instructionsText);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset(false);
            }
        });
        control_panel.add(reset);

        // instructionsButton creation and inclusion in the control panel
        final JButton instructionsButton = new JButton("Instructions");

        instructionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                instructionsPane.setVisible(!instructionsPane.isVisible());
            }
        });
        control_panel.add(instructionsButton);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset(loadPrevious);
    }
}
