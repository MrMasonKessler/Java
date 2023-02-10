package minesweeper.model.model;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperTest {

    Minesweeper testMine = new Minesweeper(2, 2, 1);


    @Test
    public void testPrintingMinesweeper(){
        String expected = testMine.toString();
        String result = "Game state: NOT_STARTED, Number of rows: 2, Number of columns: 2, Number of mines: 1";

        assert expected.equals(result);
    }

    @Test
    public void testPrintingBoardAndInfo(){  
        testMine.printBoard();

        System.out.println("-   -");
        System.out.println("-   -");
        System.out.println("1 1");
        System.out.println("1 M");

        assert testMine.getCols() == 2 && testMine.getRows() == 2 && testMine.getMineCount() == 1;
    }

    @Test
    public void testMakeSelectionTrue() throws MinesweeperException{
        Location testLoc = new Location(0, 0);
        testLoc.setSymbol(Symbol.UNCOVERED_SAFE_1);
        testMine.makeSelection(testLoc);

        Location realLoc = testMine.returnboard()[0][0];

        assert realLoc.getCol() == testLoc.getCol() && realLoc.getRow() == testLoc.getRow() && testLoc.getSymbol() == realLoc.getSymbol();
    }

    @Test(expected = MinesweeperException.class)
    public void testMakeSelectionFalse() throws MinesweeperException{
        Location testLoc = new Location(5, 5);
        testMine.makeSelection(testLoc);
    }

    @Test
    public void gameWon() throws MinesweeperException{
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(0, 1);
        Location loc3 = new Location(1, 0);
        testMine.makeSelection(loc1);
        testMine.makeSelection(loc2);
        testMine.makeSelection(loc3);

        assert testMine.getGameState() == GameState.WON;
    }

    @Test
    public void gameInProgressOrLost() throws MinesweeperException{ 
        Location loc1 = new Location(1, 1);
        Location loc4 = new Location(0, 0);
        Location loc2 = new Location(0, 1);
        Location loc3 = new Location(1, 0);
        //testMine.makeSelection(loc1);
        Location[][] testBoard = new Location[2][2];
        loc4.setSymbol(Symbol.UNCOVERED_MINE);
        loc1.setSymbol(Symbol.UNCOVERED_SAFE_1);
        loc2.setSymbol(Symbol.UNCOVERED_SAFE_1);
        loc3.setSymbol(Symbol.UNCOVERED_SAFE_1);

        testBoard[1][1] = loc1;
        testBoard[0][0] = loc4;
        testBoard[0][1] = loc2;
        testBoard[1][0] = loc3;

        testMine.makeField(testBoard);
        testMine.makeSelection(loc4);
        
        //assert testMine.getGameState().equals(GameState.LOST) || testMine.getGameState().equals(GameState.IN_PROGRESS);
        assert testMine.getGameState() == GameState.LOST || testMine.getGameState() == GameState.IN_PROGRESS;
    }

    @Test
    public void gameLost() throws MinesweeperException{      
        Minesweeper minetest = new Minesweeper(1, 1, 1);
        Location fail  = new Location(0, 0);
        //fail.setSymbol(Symbol.UNCOVERED_MINE);
        minetest.makeSelection(fail);

        assert fail.getSymbol() == Symbol.UNCOVERED_MINE;
    }

    @Test
    public void countNeighbors() throws MinesweeperException{
        Location point = new Location(0, 0);
        
        assert testMine.neighbors(point).size() == 3;
    }

    @Test
    public void countAdjMines() throws MinesweeperException{
        Location point = new Location(0, 0);

        assert testMine.adjacentMines(testMine.neighbors(point)) == '0' || testMine.adjacentMines(testMine.neighbors(point)) == '1';
    }


    public static void main(String[] args) throws MinesweeperException {
        //MinesweeperTest testFunc = new MinesweeperTest();
        //testFunc.countAdjMines();
    }
    
}
