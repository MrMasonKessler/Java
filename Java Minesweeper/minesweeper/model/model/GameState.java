package minesweeper.model.model;

public enum GameState {
    NOT_STARTED, IN_PROGRESS, WON, LOST;

    private GameState state;

    public GameState getState() {
        return state;
    }

    public void setState(GameState state){
        this.state = state;
    }
}
