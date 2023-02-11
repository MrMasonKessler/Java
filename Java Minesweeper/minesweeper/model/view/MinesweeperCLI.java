package minesweeper.model.view;

import java.util.List;
//import java.util.Collection;
import java.util.Scanner;

//import backtracker.Backtracker;
//import backtracker.Configuration;
import minesweeper.model.model.Location;
//import minesweeper.model.model.Location;
import minesweeper.model.model.Minesweeper;
import minesweeper.model.model.MinesweeperConfig;
//import minesweeper.model.model.MinesweeperConfig;
import minesweeper.model.model.MinesweeperException;
import minesweeper.model.model.Symbol;


//Actually plays the game


public class MinesweeperCLI {
    
    private static void help(){

        System.out.println("Available commands: ");
        System.out.println("help: shows list of commands");
        System.out.println("pick: pick row col makes a guess on the board");
        System.out.println("hint: displays an available move");
        System.out.println("reset: starts over with the same size and mineCount");
        System.out.println("quit: duh");
        System.out.println("solve: does the work for y'all lazy bums");
    }

    private static String hint(Minesweeper minesweeper){ 
        List<Location> hintLoc = minesweeper.getHint2();
        Location hintlocation1 = hintLoc.get(0);
        int col = hintlocation1.getCol();
        int row = hintlocation1.getRow();

        String location = "row:" + row + " col:" +col;
        return location;
    }

    private static void quit(){
        System.exit(0);
    }

    private static void solve(Minesweeper minesweeper){
        
        MinesweeperConfig mineConfig = new MinesweeperConfig(minesweeper);
        System.out.println(mineConfig.getSuccessors());
        
    }

    public static void main(String[] args) throws MinesweeperException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter # of rows, # of cols, # of mines, seperated by spaces: ");
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        int mineCount = scanner.nextInt();
        if(rows<0 || cols<0 || ((rows==1)&&(cols==1)) || mineCount > (rows*cols)){
            scanner.close();
            throw new MinesweeperException("Invalid input");
        }
        scanner.nextLine();
        Minesweeper play = new Minesweeper(rows, cols, mineCount);
        System.out.print("Type help to get started: ");
        while(true){
            System.out.println("");
            System.out.print(">> ");
            String test = scanner.nextLine();
            String[] testSplit = test.split(" ");
            if (test.equals("help")){
                help();
                System.out.println("");
            }
            //EVERY TIME YOU WANT TO REVEAL A NEW SPOT, YOU MUST CALL PICK FIRST
            if(testSplit[0].equals("pick")){
                int row = Integer.parseInt(testSplit[1]);
                int col = Integer.parseInt(testSplit[2]);
                minesweeper.model.model.Location spot = new minesweeper.model.model.Location(row, col);
                if(spot.getSymbol() != Symbol.COVERED) {
                    System.out.println("Spot already uncovered!");
                    play.printBoard();
                }
                if(spot.getSymbol()==Symbol.COVERED){
                    play.makeSelection(spot);
                }
                if (play.getBoard(spot).getSymbol()==Symbol.UNCOVERED_MINE){
                    System.out.println("You lose!");
                    System.out.println("");
                    play.printBoard();
                    System.out.println("");
                    System.out.print("Play again? yes/no: ");
                    String answer = scanner.nextLine();
                    if(answer.equals("yes")){
                        play = new Minesweeper(rows, cols, mineCount);
                        for(int i=0;i<50;i++){
                            System.out.println();
                        }
                        //play.getVisibleBoard();
                    }
                    else if(answer.equals("no")){
                        System.exit(0);
                    }
                }
                if (mineCount==0){
                    System.out.println("You win! Nice job!");
                    play.printBoard();
                    scanner.close();
                    System.exit(0);
                }
                System.out.println("");
            }
            if (test.equals("hint")){
                System.out.println(hint(play));
            }
            if (test.equals("reset")){
                for(int i=0;i<50;i++){
                    System.out.println("");
                }
                play = new Minesweeper(rows, cols, mineCount);
                for(int i=0;i<10;i++){
                    System.out.println("");
                }
            }
            if (test.equals("quit")){
                quit();
            }
            if (test.equals("solve")){
                solve(play);
            }
            play.getVisibleBoard();
            if((rows*cols)-mineCount==play.getmoveCount()){
                scanner.nextLine();
                play.setMoveCount(0);
                System.out.print("Wanna play the same board again, yes/no: ");
                String answer = scanner.nextLine();
                
                if(answer.equals("yes")){
                    for(int i=0;i<10;i++){
                        System.out.println("");
                    }
                    play = new Minesweeper(rows,cols,mineCount);
                    play.getVisibleBoard();
                }
                else if(answer.equals("no")){
                    System.exit(0);
                }
            }
        }
    } 

}