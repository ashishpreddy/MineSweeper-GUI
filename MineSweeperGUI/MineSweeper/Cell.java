package org.MineSweeperGUI.MineSweeper;

/**
 * CIS 120 HW09 - MineSweeper
 * (c) University of Pennsylvania
 * Created by Ashish Pothireddy in Fall 2021.
 */

/**
 * This class instantiates a Cell object, which constitutes the individual
 * components of the MineSweeper Board. As the user interacts with the game
 * board, the individual cells are updated,
 * <p>
 * The Cell class contains important information that pertains to the
 * implementation
 * and testing of the class.
 * <p>
 * <p>
 * This game adheres to a Model-View-Controller design framework, and is
 * intended to be
 * as modular as possible.
 */
public class Cell {

    // instance variables associated with the Cell class
    private boolean isMine = false;
    private boolean isFlagged = false;
    private int numMines = 0;
    private boolean isClicked = false;

    public Cell() {
    }

    /**
     * Constructor sets up game state.
     *
     * @param isMine lets constructor know if current cell is a mine
     */
    public Cell(boolean isMine) {
        this.isMine = isMine;
        if (isMine) {
            this.numMines = -1;
        }
    }

    /**
     * Gets the isMine status of the cell
     *
     * @return boolean informing if the current cell is a mine
     */
    public boolean getIsMine() {
        return isMine;
    }

    /**
     * Sets the isMine status of the cell
     *
     * @param isMine informs the handler if the cell is a mine
     */
    public void setIsMine(boolean isMine) {
        this.isMine = isMine;
        if (isMine) {
            this.numMines = -1;
        }
    }

    /**
     * Gets the isFlagged status of the cell
     *
     * @return boolean letting know if the cell is flagged
     */
    public boolean getIsFlagged() {
        return isFlagged;
    }

    /**
     * Sets the isFlagged status of the cell
     *
     * @param isFlagged informs the handler if the cell is being flagged
     */
    public void setIsFlagged(boolean isFlagged) {
        this.isFlagged = isFlagged;
    }

    /**
     * Gets the numMines status of the cell
     *
     * @return numMines informs the handler of the number of surrounding mines
     */
    public int getMineNumber() {
        return numMines;
    }

    /**
     * Sets the numMines status of the cell
     *
     * @param x informs the handler of the number of surrounding mines
     */
    public void setMineNumber(int x) {
        numMines = x;
    }

    /**
     * Gets the isClicked status of the cell
     *
     * @return isClicked informs the handler if the cell has been clicked
     */
    public boolean getIsClicked() {
        return isClicked;
    }

    /**
     * Sets the isClicked status of the cell
     *
     * @param isClicked informs the handler if the cell is now clicked
     */
    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

}
