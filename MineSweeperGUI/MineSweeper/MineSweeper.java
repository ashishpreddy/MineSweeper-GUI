package org.MineSweeperGUI.MineSweeper;


import java.io.*;

public class MineSweeper {

    // instance variables associated with the MineSweeper class
    private final int xDimension;
    private final int yDimension;
    private int moveCounter;
    private int gameOver;
    private long startTime;
    private int minutes;
    private int seconds;
    private int numMines;

    /**
     * Constructor sets up game state.
     */
    public MineSweeper() {
        xDimension = 10;
        yDimension = 10;
        moveCounter = 0;
        gameOver = 0;
        startTime = System.currentTimeMillis();
        seconds = 0;
        minutes = 0;
    }

    /**
     * Gets the move counter
     *
     * @return moveCounter noting the number of moves made
     */
    public int getMoveCounter() {
        return moveCounter;
    }

    /**
     * Gets the status of the game
     * 0 --> game is not done, 1 --> game over and user won, 2 --> game over and
     * user lost
     *
     * @return gameOver noting the status of the game
     */
    public int getStatus() {
        return gameOver;
    }

    /**
     * Gets the minutes of the total game play
     *
     * @return minutes noting the num of minutes taken to finish the game
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Sets the minutes associated with the duration of gameplay
     *
     * @param min
     */
    public void setMinutes(int min) {
        minutes = min;
    }

    /**
     * Gets the seconds of the total game play
     *
     * @return seconds noting the num of seconds taken to finish the game
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Sets the seconds associated with the duration of gameplay
     *
     * @param sec
     */
    public void setSeconds(int sec) {
        seconds = sec;
    }

    /**
     * Gets the long associated with the start time of the system
     *
     * @return startTime holding the start of the game
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Sets the startTime associated with the System Current Time
     *
     * @param start
     */
    public void setStartTime(long start) {
        startTime = start;
    }

    /**
     * Creates the gameBoard with a random placement of mines with a random number
     * of mines.
     * Code ensures there are no repeat placement of mines and that mines are placed
     * within
     * the bounds of the gameBoard. Makes a call to countAdjacementMines which
     * further modifies
     * the gameBoard object, populating it with the number of adjacent mines for
     * non-mine cells.
     *
     * @param x            noting the lower bound for the number of mines
     * @param y            noting the upper bound for the number of mines
     * @param loadPrevious contains the boolean indicating if we load the previous
     *                     game
     * @return Cell[][] gameBoard the gameBoard that handles the major logic of the
     *         gameplay
     */
    public Cell[][] gameBoard(int x, int y, boolean loadPrevious) {
        if (loadPrevious) {
            return recreateGame();
        }
        Cell[][] msGameBoard = new Cell[xDimension][yDimension];
        numMines = (int) (Math.random() * (y - x)) + x;
        int[] xValues = new int[numMines];
        int[] yValues = new int[numMines];
        for (int i = 0; i < numMines;) {
            boolean insert = true;
            int randX = (int) (Math.random() * xDimension);
            int randY = (int) (Math.random() * yDimension);
            for (int j = 0; j < i; j++) {
                if (xValues[j] == randX && yValues[j] == randY) {
                    insert = false;
                    j = i;
                }
            }
            if (insert) {
                xValues[i] = randX;
                yValues[i] = randY;
                i++;
            }
        }
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                msGameBoard[i][j] = new Cell();
            }
        }
        for (int i = 0; i < xValues.length; i++) {
            msGameBoard[xValues[i]][yValues[i]] = new Cell(true);
        }
        setAdjacentValues(msGameBoard);
        return msGameBoard;
    }

    /**
     * Modifies the gameBoard, updating individual cells to reflect how many
     * adjacent mines
     * are there. This method moreover updates the content of the cells to reflect
     * the new values
     * that can be printed via the printBoard method.
     *
     * @param game which is the main gameBoard
     */
    public void setAdjacentValues(Cell[][] game) {
        int adjacentMineCount = 0;
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                for (int z = i - 1; z <= i + 1; z++) {
                    for (int w = j - 1; w <= j + 1; w++) {
                        if (!(w < 0) && !(z < 0) && (z < xDimension) && (w < yDimension)) {
                            if (game[z][w].getIsMine() && !(w == j && z == i)) {
                                adjacentMineCount++;
                            }
                        }
                    }
                }

                if (game[i][j].getIsMine()) {
                    game[i][j].setMineNumber(-1);
                } else {
                    game[i][j].setMineNumber(adjacentMineCount);
                }
                adjacentMineCount = 0;
            }
        }
    }

    /**
     * Contains the major play logic for when the user selects a cell they want to
     * click.
     * The play logic checks first if we are flagging or clicking and then chooses
     * one of two
     * pathways to operate on. If it chooses to flag, then it simply increments the
     * moveCounter
     * and flags the cell. If it is clicking, then if checks if the cell is a mine
     * and ends the
     * game if so. If not, it calls the recOpenCells method (assuming cll is 0) in
     * order to
     * recursively open nearby cells. Otherwise, it sets the cell to be clicked. If
     * the game
     * ends, then the end time is recorded and minutes/seconds are updated.
     *
     * @param game    contains the gameBoard
     * @param x       contains the x value corresponding to the necessary cell in
     *                the gameBoard
     * @param y       contains the y value corresponding to the necessary cell in
     *                the gameBoard
     * @param isRight contains a boolean indicating whether we are flagging or
     *                clicking
     */
    public void play(Cell[][] game, int x, int y, boolean isRight) {
        if (x > 9 || y > 9 || x < 0 || y < 0) {
            return;
        }
        if (gameOver == 1 || gameOver == 2) {
            return;
        }
        if (!isRight) {
            if (game[x][y].getIsFlagged()) {
                return;
            } else if (game[x][y].getIsMine()) {
                moveCounter++;
                gameOver = 2;
            } else {
                if (game[x][y].getIsClicked()) {
                    return;
                } else if (game[x][y].getMineNumber() == 0) {
                    recOpenCells(x, y, game);
                } else {
                    game[x][y].setIsClicked(true);
                }
                moveCounter++;
                gameOver = checkGameStatus(game);
            }
        } else {
            if (game[x][y].getIsClicked()) {
                return;
            }
            game[x][y].setIsFlagged(!game[x][y].getIsFlagged());
            moveCounter++;
        }
        writeToCSV(game);
        if (gameOver == 1 || gameOver == 2) {
            int timeSeconds = (int) ((System.currentTimeMillis() - startTime) / 1000);
            minutes = minutes + timeSeconds / 60;
            seconds = seconds + timeSeconds % 60;
            if (seconds >= 60) {
                minutes += 1;
                seconds -= 60;
            }
            File f = new File("moves.csv");
            f.delete();
        }
    }

    /**
     * Writes the current game state to the file. When the game is terminated,
     * additional
     * information through a separate method is written to the file, logging the
     * moves
     * made and time taken altogether.
     *
     * @param game contains the gameBoard
     */
    public void writeToCSV(Cell[][] game) {
        File f = new File("moves.csv");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {
            }
        }
        try {
            FileWriter fw = new FileWriter(f);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < yDimension; i++) {
                for (int j = 0; j < xDimension; j++) {
                    String s = game[j][i].getMineNumber() + "";
                    if (game[j][i].getIsFlagged()) {
                        s += "@";
                    } else if (!game[j][i].getIsClicked()) {
                        s += "~";
                    }
                    sb.append(s);
                    if (j != (xDimension - 1)) {
                        sb.append(',');
                    }
                }
                if (i != (yDimension - 1)) {
                    sb.append("\n");
                }
            }
            fw.write(sb.toString());
            long currTime = System.currentTimeMillis();
            int timeSeconds = (int) ((currTime - startTime) / 1000);
            int currMinutes = timeSeconds / 60;
            int currSeconds = timeSeconds % 60;
            fw.write("\n" + moveCounter + "," + currMinutes + "," + currSeconds + "," + gameOver);
            fw.close();
        } catch (IOException e) {
        }
    }

    /**
     * Creates a brand-new gameboard based on the existing csv file that documents
     * the previous
     * iteration. This method is only called if the game is not over in some way
     * shape or form.
     * It relies on the invariants established in the MineSweeper game (that there
     * are set
     * dimensions, that you cannot flag a clicked cell, etc.).
     *
     * @return game contains the recreated gameBoard
     */
    public Cell[][] recreateGame() {
        File f = new File("moves.csv");
        if (!f.exists()) {
            return null;
        }
        Cell[][] game = new Cell[xDimension][yDimension];
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                game[i][j] = new Cell();
            }
        }
        int xCounter = 0;
        int yCounter = 0;
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            while (xCounter < xDimension && yCounter < yDimension) {
                String currLine = br.readLine();
                if (currLine == null) {
                    break;
                }
                String[] splitted = currLine.split(",");
                for (String s : splitted) {
                    if (s.length() > 2) {
                        game[xCounter][yCounter].setIsMine(true);
                        game[xCounter][yCounter].setIsClicked(false);
                        game[xCounter][yCounter].setMineNumber(-1);
                        if (s.endsWith("@")) {
                            game[xCounter][yCounter].setIsFlagged(true);
                        }
                    } else if (s.length() > 1) {
                        int num = Integer.parseInt(s.substring(0, 1));
                        game[xCounter][yCounter].setMineNumber(num);
                        game[xCounter][yCounter].setIsClicked(false);
                        if (s.endsWith("@")) {
                            game[xCounter][yCounter].setIsFlagged(true);
                        }
                    } else {
                        game[xCounter][yCounter].setIsClicked(true);
                        int num = Integer.parseInt(s);
                        game[xCounter][yCounter].setMineNumber(num);
                    }
                    xCounter++;
                }
                xCounter = 0;
                yCounter++;
            }
            String[] splitted = br.readLine().split(",");
            startTime = System.currentTimeMillis();
            moveCounter = Integer.parseInt(splitted[0]);
            minutes = Integer.parseInt(splitted[1]);
            seconds = Integer.parseInt(splitted[2]);
        } catch (Exception e) {
        }
        return game;
    }

    /**
     * Checks if the game has been won. Only two possibilities: game is still
     * running or game
     * is won by user (game is lost is handled in play method). The method first
     * assumes that the user has won but changes that assumption if a cell is
     * neither open nor
     * clicked.
     *
     * @param game contains the gameBoard
     * @return int winner
     */
    public int checkGameStatus(Cell[][] game) {
        int winner = 1;
        for (int i = 0; i < xDimension; i++) {
            for (int j = 0; j < yDimension; j++) {
                if (!game[i][j].getIsClicked() && !game[i][j].getIsMine()) {
                    winner = 0;
                    break;
                }
            }
        }
        return winner;
    }

    /**
     * Contains the logic for revealing surrounding cells for 0-value cells. If the
     * cell is 0,
     * the method iterates through the surrounding cells and opens them. If a
     * surrounding cell
     * is also 0, recOpenCells is recursively called in order to check the
     * surrounding cells
     * of the nearby 0-value cell. The base case checks if the current cell is
     * clicked. If it is,
     * the method terminates.
     *
     * @param x    contains the x value corresponding to the current cell in the
     *             GameBoard
     * @param y    contains the y value corresponding to the current cell in the
     *             GameBoard
     * @param game contains the gameBoard
     */
    public void recOpenCells(int x, int y, Cell[][] game) {
        if (game[x][y].getIsClicked()) {
            return;
        }

        game[x][y].setIsClicked(true);

        if (game[x][y].getMineNumber() == 0) {
            for (int i = (x - 1); i <= (x + 1); i++) {
                for (int j = (y - 1); j <= (y + 1); j++) {
                    if (!(i < 0) && !(j < 0) && (i < xDimension) && (j < yDimension)) {
                        recOpenCells(i, j, game);
                    }
                }
            }
        }
    }
}
