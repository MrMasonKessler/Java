package minesweeper.model.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.model.model.GameState;
import minesweeper.model.model.Location;
import minesweeper.model.model.Minesweeper;
import minesweeper.model.model.MinesweeperException;
import minesweeper.model.model.Symbol;




//WITH THE HINT BUTTON, THE GREEN BOX NEEDS TO BE CLICKED MULTIPLE TIMES TO SHOW THE UNDERNEATH?



public class MinesweeperGUI extends Application{

    public static final Image flag = new Image("file:media/images/flag.png");
    public static final Image mine = new Image("file:media/images/mine24.png");
    public static final Image blankSquare = new Image("file:media/images/covered.png"); 
    public static final Image uncoveredBlankSquare = new Image("file:media/images/uncovered_blank_zero.png");
    public static final Image one = new Image("file:media/images/one.png");
    public static final Image two = new Image("file:media/images/two.png");
    public static final Image three = new Image("file:media/images/three.png");
    public static final Image four = new Image("file:media/images/four.png");
    public static final Image five = new Image("file:media/images/five.png");
    public static final Image six = new Image("file:media/images/six.png");
    public static final Image seven = new Image("file:media/images/seven.png");
    public static final Image eight = new Image("file:media/images/eight.png");
    public static final Image hint = new Image("file:media/images/hint.png");
    

    private Button[][] buttons;
    

    private Minesweeper game;

    private GridPane boardView = new GridPane();

    public static Label numCountLabel;
    public static Label descriptionLabel;


    public List<Node> getNodesToRemove(GridPane pane, int row, int col){

        ObservableList<Node> childsPlay = pane.getChildren();
        List<Node> nodeList = new ArrayList<>();

            for(Node node : childsPlay){
                int paneRow = GridPane.getRowIndex(node);
                int paneCol = GridPane.getColumnIndex(node);
                if(paneRow == row && paneCol == col && node instanceof Button){
                    nodeList.add(node);
                }
            }
        return nodeList;
    }

    public Image getPicToPlace(Symbol toSwitch){
        Image pic = null; 

        if(toSwitch == Symbol.UNCOVERED_MINE){
            pic = mine;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_0){
            pic = uncoveredBlankSquare;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_1){
            pic = one;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_2){
            pic = two;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_3){
            pic = three;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_4){
            pic = four;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_5){
            pic = five;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_6){
            pic = six;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_7){
            pic = seven;
        }else if(toSwitch == Symbol.UNCOVERED_SAFE_8){
            pic = eight;
        }
        return pic;
    }

    public List<Node> getNodesToFlip(GridPane pane){

        ObservableList<Node> childsPlay = pane.getChildren();
        List<Node> nodeList = new ArrayList<>();
        
        int row ; 
        int col ;

        for(Node node : childsPlay){
            row = GridPane.getRowIndex(node); 
            col = GridPane.getColumnIndex(node);

            Location newLoc = new Location(row, col);
            Symbol flipBitch = (game.getBoard(newLoc)).getSymbol();

            if( flipBitch != Symbol.COVERED ){
                nodeList.add(node);
            } 
        }
       
        return nodeList;
    }

    public Symbol getSymbolFromRowCol(int row, int col){

        if(row>=0 && col>=0){
            Location initLoc = new Location(row, col); 
            Symbol initSymbol = game.getBoard(initLoc).getSymbol();
            return initSymbol;
        }
        return null;
    }

    public Node findNode(GridPane pane, int row, int col){

        ObservableList<Node> orphans = pane.getChildren();
        int Nrow; 
        int Ncol;

        Node theOneYouSeek = null;
        for(Node node : orphans){
            
            Nrow = GridPane.getRowIndex(node); 
            Ncol = GridPane.getColumnIndex(node);


            if(Nrow == row && Ncol == col){
                theOneYouSeek = node;
            } 
            else{
                continue;
            }
            return theOneYouSeek;
        }
        return theOneYouSeek;
    }

    public void flipSingleButton(GridPane pane, int row, int col){

        if(findNode(pane, row, col) != null){

            Node node = findNode(pane, row, col);
            
            Button n = (Button)node;
            Button newButton = new Button();

            Location loc = new Location(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)); 

            pane.getChildren().remove(n);
            n.setDisable(true);
            newButton.setBackground(new Background(new BackgroundImage(getPicToPlace((game.getBoard(loc)).getSymbol()), 
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            BackgroundSize.DEFAULT))); 

            pane.add(newButton, loc.getCol(), loc.getRow());
            newButton.setDisable(true);

        }
    }


    public void showNeighboringSquares(GridPane pane, int Rinit, int Cinit){
        Symbol initSymbol = getSymbolFromRowCol(Rinit, Cinit);
        Location currentLocation = new Location(Rinit, Cinit);
        if (initSymbol == Symbol.UNCOVERED_SAFE_0){
            List<Location> revealedSquares = game.neighbors(currentLocation);
            for (int i = 0; i < revealedSquares.size(); i++){
                makeMove(pane, revealedSquares.get(i).getRow(), revealedSquares.get(i).getCol());
            }
        }
    }
        /*
        Thought process of this method is that it will remove the 8 squares around the one initially chosen, 
        but only if the square chosen is a blank Symbol or an UNCOVERED_BLANK_0. A square will not be removed 
        if the next uncovered square is going to be a mine. I still have no idea how to make this go to every
        touching uncovered square (maybe a mixture of recursion and a HashSet that will be able to quickly
        check to make sure a tile was not already looked at and flipped by the method), 
        but that is a problem for the morning. 

            N.W   N   N.E
              \   |   /
               \  |  /
            W----Cell----E
                 / | \
               /   |  \
            S.W    S   S.E
 
        Cell-->Current Cell (row, col)
        N -->  North        (row-1, col)
        S -->  South        (row+1, col)
        E -->  East         (row, col+1)
        W -->  West         (row, col-1)
        N.E--> North-East   (row-1, col+1)
        N.W--> North-West   (row-1, col-1)
        S.E--> South-East   (row+1, col+1)
        S.W--> South-West   (row+1, col-1)
        */
        

    public void revealAllSquares(GridPane pane){
        
        List<Node> hitList = getNodesToFlip(pane);

        for(Node node : hitList){

            Button n = (Button)node; 
            Button newButton = new Button();

            Location loc = new Location(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)); 

            pane.getChildren().remove(n);
            n.setDisable(true);
            newButton.setBackground(new Background(new BackgroundImage(getPicToPlace((game.getBoard(loc)).getSymbol()), 
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            BackgroundSize.DEFAULT))); 

            pane.add(newButton, loc.getCol(), loc.getRow());
            newButton.setDisable(true);

        }
        
    }

    public void makeMove(GridPane pane, int row, int col){
        try{
            Location newLoc = new Location(row, col);
            game.makeSelection(newLoc);

            Symbol toSwitch = (game.getBoard(newLoc)).getSymbol();
            Image pic = null;

            List<Node> toRemove = getNodesToRemove(pane, row, col);

            for(Node node : toRemove){
                
                pic = getPicToPlace(toSwitch);

                Button n = (Button)node; 
                Button newButton = new Button();

                pane.getChildren().remove(n);
                n.setDisable(true);

                newButton.setBackground(new Background(new BackgroundImage(pic, 
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                BackgroundSize.DEFAULT))); 

                pane.add(newButton, col, row);
                newButton.setDisable(true);


                if(game.getBoard(newLoc).getSymbol()==Symbol.UNCOVERED_MINE){
                    revealAllSquares(pane);
                    descriptionLabel.setText("Boom! Better luck next time!");
                    descriptionLabel.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
                }
                
            }

            numCountLabel.setText("" + game.getmoveCount());

            if(game.getmoveCount()==(game.getCols()*game.getRows())-game.getMineCount()){
                descriptionLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                descriptionLabel.setText("You win!!!");
                revealAllSquares(pane);
            }
        }
    
        catch(MinesweeperException me){
            System.err.println("Bad move");
        }
    }

    public void placeHintButton(GridPane pane, int row, int col){

        if(findNode(pane, row, col) != null){

            Node node = findNode(pane, row, col);
            
            Button n = (Button)node;
            Button newButton = new Button();

            Location loc = new Location(GridPane.getRowIndex(node), GridPane.getColumnIndex(node)); 

            pane.getChildren().remove(n);
            n.setDisable(true);
            newButton.setBackground(new Background(new BackgroundImage(hint, 
            BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            BackgroundSize.DEFAULT))); 

        
            newButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    game.setMoveCount(game.getmoveCount()+1);
                }
            });
            newButton.setOnAction(new MoveMaker(row, col, this, boardView));

            pane.add(newButton, loc.getCol(), loc.getRow());
            
        }

    }


    public void hint(GridPane pane, List<Location> locList) {

        Random number = new Random();
        
        List<Location> hintLocations = locList;
        //List<Location> hintLocations = game.getHint2();
        if (hintLocations.size() == 0){
            System.out.println("No more squares");    
        }
        
        if(game.getGameState()!=GameState.LOST){
            int numbers = number.nextInt(hintLocations.size());
            Location hintlocation = hintLocations.get(numbers);
            int row = hintlocation.getRow();
            int col = hintlocation.getCol();
    
            placeHintButton(pane, row, col);
            
            locList.remove(hintLocations.get(numbers));
        }
        

    }

    @Override
    public void start(Stage stage) throws Exception {
        game = new Minesweeper();

        List<Location> hintLocations = game.getHint2();
        
        Button reset = new Button("RESET");
        reset.setAlignment(Pos.BASELINE_CENTER);
        reset.setMaxWidth(Double.POSITIVE_INFINITY);
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                hintLocations.clear();
                game = new Minesweeper();
                game.setMoveCount(0);
                descriptionLabel.setText("");
                descriptionLabel.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
                numCountLabel.setText("" + game.getmoveCount());

                List<Location> newLocations = game.getHint2();
                
                for(int i=0; i<game.getRows();i++){
                    for(int j=0; j<game.getCols();j++){
                        try {
                            boardView.add(makeMinesweeperButton(i, j, game), j, i);
                        } catch (MinesweeperException e) {
                            System.err.println("Something went wrong");
                        }
                    }
                } 
                hintLocations.addAll(newLocations);
            }
        });


        
        Button hint = new Button("Hint!");
        hint.setAlignment(Pos.BASELINE_CENTER);
        class hintEvent implements EventHandler<ActionEvent>{
            private MinesweeperGUI gui;
            private GridPane testPain;
            private List<Location> hintLoc;

            public hintEvent(MinesweeperGUI gui, GridPane testPain, List<Location> hintLoc){
                this.gui = gui;
                this.testPain = testPain;
                this.hintLoc = hintLoc;
            }

            @Override
            public void handle(ActionEvent arg0) {
                gui.hint(testPain, hintLoc);
            }
        }
        hint.setOnAction(new hintEvent(this, boardView, hintLocations));
        hint.setMaxWidth(Double.POSITIVE_INFINITY);


        Button solve = new Button("Solve!");
        solve.setAlignment(Pos.BASELINE_CENTER);
        solve.setMaxWidth(Double.POSITIVE_INFINITY);
        solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                //solve(game);
                System.out.println("This solve button don't work yet");
                descriptionLabel.setText("Solved!");
            }
        });


        Label numMines = new Label("Number of mines left: "  + game.getMineCount());
        numMines.setPadding(new Insets(10));

        Label description = new 
        Label();
        description.setMaxWidth(Double.POSITIVE_INFINITY);
        description.setMaxHeight(Double.POSITIVE_INFINITY);
        descriptionLabel = description;
        descriptionLabel.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        

        Label numMoves = new Label("Number of moves taken: " + game.getmoveCount());
        numCountLabel = numMoves;

        
        buttons = new Button[game.getRows()][game.getCols()];
        
        for(int row = 0; row < game.getRows(); row++){
            for(int col = 0; col < game.getCols(); col++){
                Button but= makeMinesweeperButton(row,col,game);
                buttons[row][col] = but;
                boardView.add(but, col, row);
                
            }
        }

        
        boardView.setBorder(new Border(new BorderStroke(Color.DARKGREY, BorderStrokeStyle.SOLID,
        CornerRadii.EMPTY, new BorderWidths(7))));
        
        //boardView.setGridLinesVisible(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(numMines, numMoves, hint, reset, solve);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(vbox);
        borderPane.setCenter(boardView);
        borderPane.setBottom(description);

        Scene scene = new Scene(borderPane, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.show();
    }

    public Button makeMinesweeperButton(int row, int col, Minesweeper minesweeper) throws MinesweeperException{ 

        Location location = new Location(row, col); 
        Symbol symbol = minesweeper.getSymbol(location);
        ImageView pic = null; 

        if(symbol == Symbol.COVERED){
            pic = new ImageView(blankSquare);
        } else if(symbol == Symbol.UNCOVERED_MINE){
            pic = new ImageView(mine);
        }else if(symbol == Symbol.UNCOVERED_SAFE_0){
            pic = new ImageView(uncoveredBlankSquare);
        }else if(symbol == Symbol.UNCOVERED_SAFE_1){
            pic = new ImageView(one);
        }else if(symbol == Symbol.UNCOVERED_SAFE_2){
            pic = new ImageView(two);
        }else if(symbol == Symbol.UNCOVERED_SAFE_3){
            pic = new ImageView(three);
        }else if(symbol == Symbol.UNCOVERED_SAFE_4){
            pic = new ImageView(four);
        }else if(symbol == Symbol.UNCOVERED_SAFE_5){
            pic = new ImageView(five);
        }else if(symbol == Symbol.UNCOVERED_SAFE_6){
            pic = new ImageView(six);
        }else if(symbol == Symbol.UNCOVERED_SAFE_7){
            pic = new ImageView(seven);
        }else if(symbol == Symbol.UNCOVERED_SAFE_8){
            pic = new ImageView(eight);
        }

        Button button = new Button("",pic);

        button.setBackground(new Background(new BackgroundImage(blankSquare, 
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
        BackgroundSize.DEFAULT)));
        button.setPadding(new Insets(0));
        
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                game.setMoveCount(game.getmoveCount()+1);
            }
        });
        button.setOnAction(new MoveMaker(row, col, this, boardView));
        
        return button;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}