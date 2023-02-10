package minesweeper.model.model;

public enum Symbol {
    COVERED('-'), 
    UNCOVERED_SAFE_0 ('0'), 
    UNCOVERED_SAFE_1 ('1'), 
    UNCOVERED_SAFE_2 ('2'), 
    UNCOVERED_SAFE_3 ('3'), 
    UNCOVERED_SAFE_4 ('4'), 
    UNCOVERED_SAFE_5 ('5'), 
    UNCOVERED_SAFE_6 ('6'), 
    UNCOVERED_SAFE_7 ('7'), 
    UNCOVERED_SAFE_8 ('8'),  
    UNCOVERED_MINE('M');

    private char symbol; 

    private Symbol(char symbol){ 
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

}