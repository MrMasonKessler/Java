package minesweeper.model.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

//import backtracker.Backtracker;
import backtracker.Configuration;


//check all nodes, until find one that isn't a mine
//reveal that one, 


public class MinesweeperConfig implements Configuration{

    private Location[] locations = new Location[1];
    private Minesweeper minesweeper;
    private Location location;
    private int n;

    public MinesweeperConfig(Minesweeper minesweeper){
        this.minesweeper = minesweeper;
    }

    public MinesweeperConfig(Minesweeper minesweeper, Location location, Location[] locations, int n){
        this.minesweeper = minesweeper;
        this.location = location;
        this.locations = locations;
        this.n = n;
    }

    @Override
    public String toString() {
        return "" + getSuccessors();
    }

    @Override
    public Collection<Configuration> getSuccessors() {


        //Line 48 isn't working, since apparently locations[0] is null.


        List<Configuration> successors = new ArrayList<>();
        int arrayLength = locations.length;
        int row = locations[arrayLength-1].getRow();
        if(row < n){
            for(int col = 0; col < n; col++){
                Location[] newLocations = Arrays.copyOf(locations, arrayLength+1); //deep copy
                Location spot = new Location(row, col);
                newLocations[arrayLength] = spot;
                MinesweeperConfig successor = new MinesweeperConfig(minesweeper, location, locations, n);
                successors.add(successor);
            }
        }
        return successors;
    }

    @Override
    public boolean isValid() {
        if(location!=null){
            try {
                if(minesweeper.getSymbol(location)!=Symbol.UNCOVERED_MINE && minesweeper.isCovered(location)){
                    return true;
                }
            } catch (MinesweeperException e) {}
        }
        else{
            return false;
        }
        return minesweeper.getGameState()!=GameState.LOST;
    }

    @Override
    public boolean isGoal() {
        return (isValid()==true && locations.length == n);
    }

    public Location[] getLocations() {
        return locations;
    }

    // public static void solve(Minesweeper minesweeper){
    //     Backtracker backtracker = new Backtracker(false);
    //     Configuration solved = backtracker.solve(new MinesweeperConfig(minesweeper));
    // }


}
