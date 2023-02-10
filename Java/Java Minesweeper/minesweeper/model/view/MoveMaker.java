package minesweeper.model.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;

public class MoveMaker implements EventHandler<ActionEvent> {

    private int row; 
    private int col; 
    private MinesweeperGUI gui; 

    private GridPane testPain;

    /*
    public MoveMaker(int row, int col, MinesweeperGUI gui){
        this.row = row;
        this.col = col; 
        this.gui = gui;
    }
    */


    public MoveMaker(int row, int col, MinesweeperGUI gui, GridPane testPain){
        this.row = row;
        this.col = col; 
        this.gui = gui;
        this.testPain = testPain;
    }


    @Override
    public void handle(ActionEvent arg0) {
        //gui.makeMove(row, col);
        gui.makeMove(testPain, row, col);
        }

}