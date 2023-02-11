package minesweeper.model.model;

//import java.util.HashSet;
//import java.util.Set;

public class Location {
    private int row;
    private int col;
    private Symbol symbol;

    //private Set<MinesweeperObserver> observers = new HashSet<>();

    public Location(int row, int col){
        this.row = row;
        this.col = col;
        this.symbol = Symbol.COVERED;
        
    }

    // public void register(MinesweeperObserver observer){
    //     observers.add(observer);
    // }

    public boolean isCovered() {
        return this.getSymbol() == Symbol.COVERED;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public char getChar(Symbol symbol){
        if(symbol==Symbol.UNCOVERED_MINE){
            return 'M';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_0){
            return '0';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_1){
            return '1';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_2){
            return '2';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_3){
            return '3';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_4){
            return '4';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_5){
            return '5';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_6){
            return '6';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_7){
            return '7';
        }
        if(symbol==Symbol.UNCOVERED_SAFE_8){
            return '8';
        }
        if(symbol==Symbol.COVERED){
            return '-';
        }
        return 'z';
    }

    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }


    @Override
    public String toString() {
        return "Row: " + getRow() + " Col: " + getCol() + " " + isCovered();
    }
    

    public static void main(String[] args) {
        Location test = new Location(3, 7);
        System.out.println(test);
        // System.out.println(test.getCol());
        // Symbol symbol = Symbol.COVERED;
        // Symbol symbol2 = Symbol.UNCOVERED_MINE;
        // System.out.println(symbol.getSymbol());
        // System.out.println(symbol);
        // System.out.println(symbol2.getSymbol());
        // System.out.println(symbol2);
    }
    
}