package minesweeper.model.view;

import javafx.scene.image.ImageView;

import minesweeper.model.model.Location;
import minesweeper.model.model.MinesweeperObserver;
import minesweeper.model.model.Symbol;

public class MinesweeperChanger implements MinesweeperObserver{

    private ImageView image;

    public MinesweeperChanger(ImageView image){
        this.image = image;
    }

    //private Button[][] cells;
    // Minesweeper game;
    
    //public MinesweeperUpdater(Minesweeper game, Button[][] cells){
    //    this.cells = cells;
    //}

    @Override
    public void cellUpdated(Location location) {        

        if(location.getSymbol()==Symbol.UNCOVERED_MINE){
            image.setImage(MinesweeperGUI.mine);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_0){
            image.setImage(MinesweeperGUI.uncoveredBlankSquare);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_1){
            image.setImage(MinesweeperGUI.one);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_2){
            image.setImage(MinesweeperGUI.two);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_3){
            image.setImage(MinesweeperGUI.three);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_4){
            image.setImage(MinesweeperGUI.four);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_5){
            image.setImage(MinesweeperGUI.five);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_6){
            image.setImage(MinesweeperGUI.six);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_7){
            image.setImage(MinesweeperGUI.seven);
        }
        else if(location.getSymbol()==Symbol.UNCOVERED_SAFE_8){
            image.setImage(MinesweeperGUI.eight);
        }
    }
    
}