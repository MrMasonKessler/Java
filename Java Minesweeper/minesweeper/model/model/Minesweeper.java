package minesweeper.model.model;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collection;
//import java.util.HashSet;
import java.util.List;
import java.util.Random;
//import java.util.Set;

public class Minesweeper {
    
    public final char MINE = 'M';
    public final char COVERED = '-';
    private int moveCount;
    private GameState gameState;
    private Location[][] board;
    private int mineCount;
    private int rows;
    private int cols;
    private char[][] visibleBoard;
    private MinesweeperObserver observer;
    private Location currentSpot;

    public Minesweeper(int rows, int cols, int mineCount){
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.board = new Location[rows][cols];
        board = makeField(board);
        visibleBoard = new char[rows][cols];
        initVisibleBoard();
        gameState = GameState.NOT_STARTED;
    }

    public Minesweeper(){
        rows = 8;
        cols = 8;
        mineCount = 15;
        board = new Location[rows][cols];
        board = makeField(board);
        visibleBoard = new char[rows][cols];
        initVisibleBoard();
        gameState = GameState.NOT_STARTED;
    }

    public Minesweeper(Minesweeper minesweeper){
        this.rows = minesweeper.rows;
        this.gameState = minesweeper.gameState;
        this.cols = minesweeper.cols;
        this.mineCount = minesweeper.mineCount;
        this.moveCount = minesweeper.moveCount;
        this.board = new Location[rows][cols];
        Location[][] newBoard = new Location[rows][cols];
        this.visibleBoard = new char[rows][cols];
        for(int i = 0; i < minesweeper.board.length; i++){
            newBoard[i] = Arrays.copyOf(minesweeper.board[i], minesweeper.board[i].length);
        }
        char[][] newVisible = new char[rows][cols];
        for(int i = 0; i < minesweeper.visibleBoard.length; i++){
            newVisible[i] = Arrays.copyOf(minesweeper.visibleBoard[i], minesweeper.visibleBoard[i].length);
        }
        observer = null;
    }


    public void register(MinesweeperObserver observer){
        this.observer = observer;
    }



    public void notifyObserver(Location location){
        if (observer!= null){
            observer.cellUpdated(location);
        }
    }


    public Symbol getSymbol(Location location) throws MinesweeperException{
        return location.getSymbol();
    }

    public boolean isCovered(Location location) throws MinesweeperException{
        if(location.getSymbol()==Symbol.COVERED){
            return true;
        }
        else{
            throw new MinesweeperException("Pick somewhere else");
        }
    }


    //Methods for printing, getting, and setting the gameBoard and visibleBoard
    private void printBoard(char inputBoard[][]) { 
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(inputBoard[i][j] + "   ");
            }
            System.out.println();
        }
    }
    public void setVisibleBoard(char[][] board){
        visibleBoard = board;
        initVisibleBoard();
    }
    private void initVisibleBoard() { 
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                visibleBoard[i][j] = '-';
            }
        }
        printBoard(visibleBoard);
    }
    public void newVisibleBoard(){
        for(int i = 0; i<rows;i++){
            for(int j=0; j<cols;j++){
                Location spot = new Location(i,j);
                spot.setSymbol(Symbol.COVERED);
            }
        }
        getVisibleBoard();
    }
    public void getVisibleBoard() {
        for(int i = 0; i<rows;i++){
            for(int j=0; j<cols;j++){
                System.out.print(visibleBoard[i][j] + " ");
            }
            System.out.println();
        }
    }


    public void makeSelection(Location location) throws MinesweeperException{
        if(location.getRow()<0 || location.getRow()> board.length || location.getCol()<0 || location.getCol()> board[0].length){
            throw new MinesweeperException("Invalid location: (" + location.getRow() + ", " + location.getCol() + ")");
        }
        else{
            gameState = GameState.IN_PROGRESS;
            visibleBoard[location.getRow()][location.getCol()] = 
            board[location.getRow()][location.getCol()].getChar(board[location.getRow()][location.getCol()].getSymbol());
            if (board[location.getRow()][location.getCol()].getSymbol()==Symbol.UNCOVERED_MINE){
                gameState = GameState.LOST;
            }
            moveCount++;
            currentSpot = location;
        }
        if((rows*cols)-mineCount==moveCount){
            System.out.println("Nice win!");
            gameState = GameState.WON;
            printBoard();
        }
        notifyObserver(location);
    }

    //makes the field
    public Location[][] makeField(Location board[][]){
        //While there are still mines in minecount, continue picking random places on the board to put mines
        //give protection against putting in the same place
        Random random = new Random();

        for(int row=0; row<rows; row++) {
            for(int col=0; col<cols; col++) {
                board[row][col] = new Location(row,col);
            }
        }

        int mineCopy = mineCount;
        while(mineCopy!=0){
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);
            Location spot = new Location(row, col);

            if (spot.getSymbol()==Symbol.COVERED){
                if(board[row][col].getSymbol()!=Symbol.UNCOVERED_MINE){
                    board[row][col].setSymbol(Symbol.UNCOVERED_MINE);
                    mineCopy--;
                }
            }
        }
        
        for(int i = 0;i<rows;i++){
            for(int j=0;j<cols;j++){
                if(board[i][j].getSymbol()!=Symbol.UNCOVERED_MINE){
                    List<Location> neighbors = neighbors(board[i][j]);
                    char adjacent = adjacentMines(neighbors);
                    if (adjacent == '0') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_0);
                    }
                    if (adjacent == '1') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_1);
                    }
                    if (adjacent == '2') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_2);
                    }
                    if (adjacent == '3') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_3);
                    }
                    if (adjacent == '4') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_4);
                    }
                    if (adjacent == '5') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_5);
                    }
                    if (adjacent == '6') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_6);
                    }
                    if (adjacent == '7') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_7);
                    }
                    if (adjacent == '8') {
                        board[i][j].setSymbol(Symbol.UNCOVERED_SAFE_8);
                    } 
                }
            }
        }
        return board;
    }

    public List<Location> neighbors(Location point){
        // make a neighbor to keep track of it
        List<Location> neighbors = new ArrayList<>();
        boolean top_check = point.getRow() - 1 >= 0;
        boolean bottom_check = point.getRow() + 1 < board.length;
        boolean right_check = point.getCol() + 1 < board[0].length;
        boolean left_check = point.getCol() -1 >= 0;
        
        // If it pass the test add to list.
        // create location to add it.
        if (top_check){ 
            int row = point.getRow() - 1;
            Location neighbor = board[row][point.getCol()]; 
            neighbors.add(neighbor);
        }
        if (top_check && right_check){
            int row = point.getRow() -1;
            int col = point.getCol() +1;
            Location neighbor = board[row][col];
            neighbors.add(neighbor);
        }
        if (top_check && left_check){
            int row = point.getRow() -1;
            int col = point.getCol() -1;
            Location neighbor = board[row][col];
            neighbors.add(neighbor);
        }
        if (left_check){
            int col = point.getCol() -1;
            Location neighbor = board[point.getRow()][col];
            neighbors.add(neighbor);
        }
        if (right_check){
            int col = point.getCol() +1;
            Location neighbor = board[point.getRow()] [col];
            neighbors.add(neighbor);
        }
        if (bottom_check) {
            int row = point.getRow() +1;
            Location neighbor = board[row] [point.getCol()];
            neighbors.add(neighbor);
        }
        if (bottom_check && left_check){
            int row = point.getRow() +1;
            int col = point.getCol() -1;
            Location neighbor = board[row][col];
            neighbors.add(neighbor);
        }
        if (bottom_check && right_check){
            int row = point.getRow() +1;
            int col = point.getCol() +1;
            Location neighbor = board[row] [col];
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    // Returns the number of adjacent mines
    public char adjacentMines(List<Location> neighbors){
        // creating numbers of mines next to it
        int mines = 0;
        for (int i=0; i < neighbors.size(); i++) {
        if (neighbors.get(i).getSymbol() == Symbol.UNCOVERED_MINE) 
            mines++;            
        }
        
        char casted = (char)(mines+48);
        return casted;
    }

    //Our Getters and Setters
    public void setMoveCount(int count){
        moveCount = count;
    }

    public int getMineCount() {
        return mineCount;
    }

    public int getmoveCount() {
        return moveCount;
    }

    public GameState getGameState() {
        return gameState;
    }

    // public Collection<Location> getPossibleLocations() {
    //     return possibleLocations;
    // }

    public Location getCurrentSpot() {
        return currentSpot;
    }

    public void printBoard() {
        for(int i = 0; i<rows;i++){
            for(int j=0; j<cols;j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public String getHint(){
        String hint = "";
        for(int i = 0; i<rows;i++){
            for(int j = 0; j<cols;j++){
                if(visibleBoard[i][j]==COVERED && board[i][j].getChar(board[i][j].getSymbol())!=MINE){
                    hint = "Hint: row " + i + " col " + j;
                }
            }
        }
        return hint;
    }

    public List<Location> getHint2(){
        List<Location> boardlist = new ArrayList<>();

//        Set<Location> alreadyInList = new HashSet<>();

        //needed as a placeholder
        //Location hint = new Location(-1, -1);

        for (int i = 0; i<rows;i++){
            for(int j=0; j<cols; j++){
                if(visibleBoard[i][j]==COVERED && board[i][j].getChar(board[i][j].getSymbol())!=MINE){
                    Location hint = new Location(i, j);
                    boardlist.add(hint);
            }

        }
    }
//    System.out.println(boardlist);
//    for(int i = 0; i < boardlist.size(); i++){
//        if (alreadyInList.contains(boardlist.get(i))){
//            boardlist.remove(i);
//        } else {continue;}
//    }
    

	return boardlist;
}


    public Location[][] returnboard(){
        return board;
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return rows;
    }


    @Override
    public String toString() {
        //need to add more, since just gamestate doesn't tell us too much. Not needed except for testing, currently
        return "Game state: " + getGameState() + ", Number of rows: " + getRows() + ", Number of columns: " + getCols() + ", Number of mines: " + getMineCount();
    }

    public Location getBoard(Location location) {
        int i = location.getRow();
        int j = location.getCol();
        return board[i][j];
    }
    
    

    public static void main(String[] args) {
        Minesweeper test = new Minesweeper(4, 4, 3);
        System.out.println(test);
    }

}