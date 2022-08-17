package org.MineSweeperGUI.minesweeper;

import org.MineSweeperGUI.MineSweeper.Cell;
import org.MineSweeperGUI.MineSweeper.MineSweeper;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Game
 */
public class GameTest {

    @Test
    public void testClickAlreadyClickedCell() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
        assertTrue(ms[0][0].getIsClicked());
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
    }

    @Test
    public void testFlagAlreadyFlaggedCell() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, true);
        assertEquals(1, m.getMoveCounter());
        assertTrue(ms[0][0].getIsFlagged());
        m.play(ms, 0, 0, true);
        assertEquals(2, m.getMoveCounter());
        assertFalse(ms[0][0].getIsFlagged());
    }

    @Test
    public void testFlagAlreadyOpenedCell() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
        assertFalse(ms[0][0].getIsFlagged());
        m.play(ms, 0, 0, true);
        assertEquals(1, m.getMoveCounter());
        assertFalse(ms[0][0].getIsFlagged());
    }

    @Test
    public void testClickRecursivelyOpenedCell() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
        m.play(ms, 0, 1, false);
        assertEquals(1, m.getMoveCounter());
    }

    @Test
    public void testFlagRecursivelyOpenedCell() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
        assertTrue(!ms[0][0].getIsFlagged());
        m.play(ms, 0, 1, true);
        assertEquals(1, m.getMoveCounter());
        assertFalse(ms[0][0].getIsFlagged());
    }

    @Test
    public void testNumMines1() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        m.setAdjacentValues(ms);
        assertEquals(1, ms[8][9].getMineNumber());
    }

    @Test
    public void testNumMines2() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[8][9].setIsMine(true);
        m.setAdjacentValues(ms);
        assertEquals(2, ms[7][8].getMineNumber());
    }

    @Test
    public void testNumMines3() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[6][9].setIsMine(true);
        ms[7][9].setIsMine(true);
        ms[8][9].setIsMine(true);
        m.setAdjacentValues(ms);
        assertEquals(3, ms[7][8].getMineNumber());
    }

    @Test
    public void testGameIsWon() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getStatus());
    }

    @Test
    public void testGameIsLost() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 7, 9, false);
        assertEquals(2, m.getStatus());
    }

    @Test
    public void testProperRecursionOpening() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        assertEquals(1, m.getMoveCounter());
        assertEquals(0, m.getStatus());
        assertTrue(!ms[8][9].getIsClicked());
    }

    @Test
    public void testWriteToCSV() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.writeToCSV(ms);
        File f = new File("moves.csv");
        assertTrue(f.exists());
    }

    @Test
    public void testReopenNotPlayedGameBoard() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.writeToCSV(ms);
        Cell[][] game = m.recreateGame();

        assertTrue(game[7][9].getIsMine());
        assertTrue(game[9][9].getIsMine());
        assertEquals(game[8][9].getMineNumber(), 2);

        for (int i = 0; i < game.length; i++) {
            for (int j = 0; j < game[i].length; j++) {
                assertTrue(!game[i][j].getIsClicked());
            }
        }
    }

    @Test
    public void testReopenPlayedGameBoard() {
        MineSweeper m = new MineSweeper();
        Cell[][] ms = m.gameBoard(0, 0, false);
        ms[7][9].setIsMine(true);
        ms[9][9].setIsMine(true);
        m.setAdjacentValues(ms);
        m.play(ms, 0, 0, false);
        m.writeToCSV(ms);
        Cell[][] game = m.recreateGame();

        assertTrue(game[7][9].getIsMine());
        assertTrue(game[9][9].getIsMine());
        assertEquals(game[8][9].getMineNumber(), 2);
        assertTrue(game[0][0].getIsClicked());
        assertTrue(game[5][0].getIsClicked());
        assertTrue(game[0][5].getIsClicked());
        assertTrue(game[5][5].getIsClicked());
        assertEquals(m.getMoveCounter(), 1);
    }


}
