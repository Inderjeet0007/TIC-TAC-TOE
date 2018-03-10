package tictactoe;//
//Import the following classes
import javafx.animation.KeyFrame;//
import javafx.animation.KeyValue;//
import javafx.animation.Timeline;//
import javafx.application.Application;//
import javafx.event.ActionEvent;//
import javafx.event.EventHandler;//
import javafx.geometry.Pos;//
import javafx.scene.Parent;//
import javafx.scene.Scene;//
import javafx.scene.control.Button;//
import javafx.scene.control.Label;//
import javafx.scene.input.MouseButton;//
import javafx.scene.input.MouseEvent;//
import javafx.scene.layout.HBox;//
import javafx.scene.layout.Pane;//
import javafx.scene.layout.StackPane;//
import javafx.scene.layout.VBox;//
import javafx.scene.paint.Color;//
import javafx.scene.shape.Line;//
import javafx.scene.shape.Rectangle;//
import javafx.scene.text.Font;//
import javafx.scene.text.Text;//
import javafx.stage.Stage;//
import javafx.util.Duration;//


public class TicTacToe extends Application{
    private Label turn;
    VBox game_layout = new VBox();
    private Pane root = new Pane();
    Box board[][] = new Box[3][3];//
    private boolean playable = true;
    private boolean turnX = true;//
    private int sx, sy, ex, ey;//
    private Line line;
    
    @Override
    public void start(final Stage primaryStage){
        
        //Text for "Tic Tac Toe!"
        Text game_name = new Text("Tic Tac Toe!");
        game_name.setFill(Color.BLACK);
        game_name.setFont(Font.font("Verdana",72));

        //Play Button
        Button play = new Button("PLAY!");
        
        //Creating Intro Layout
        VBox intro_layout = new VBox();
        intro_layout.setSpacing(75);
        intro_layout.getChildren().addAll(game_name,play);
        intro_layout.setAlignment(Pos.CENTER);

        //Creating Intro Scene
        Scene intro = new Scene(intro_layout,600,600);
        
        //Setting Up Window
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(intro);
        primaryStage.show();
        
        //Assigning action
        play.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent event){
                Scene gameScene = new Scene(createGameScene());
                primaryStage.setScene(gameScene);
            }
            
        });
        
    }
    
    //Creating Box
    private class Box extends StackPane{
        private Text text = new Text(); //To put X or Y in the Box
        public Box(){
             //Inside the Box Constructor You Created Add these
             //This will perform an action on Mouse Click
             setOnMouseClicked(new EventHandler<MouseEvent>(){
                 public void handle(MouseEvent event){
                     //No Action if not playable
                    if(!playable)
                        return;
                    //For Entering X and O in the Box
                    if(event.getButton() == MouseButton.PRIMARY){
                        if(text.getText()=="X"||text.getText()=="O")
                            return;
                        if(turnX){
                            text.setFill(Color.RED);
                            text.setText("X");
                            turn.setText("O's Turn!");
                            turnX = false;
                        }
                        else if(!turnX){
                            text.setFill(Color.BLUE);
                            text.setText("O");
                            turn.setText("X's Turn!");
                            turnX = true;
                        }
                        setPlayable();
                    }
                 }
            });
             
            Rectangle border = new Rectangle(200,200);  //Adding Rectangle
            border.setFill(Color.AQUAMARINE);   //For Setting Background Color
            border.setStroke(Color.BLACK);  //For Setting Border Color
            
            text.setFont(Font.font("Verdana", 72)); //Set text Style
            text.setText("");       //Initialise empty text

            setAlignment(Pos.CENTER);   //Aligning Everything in Center
            getChildren().addAll(border,text);  //Adding to Box Layout
        }
        
        //Get the text value in the Box
        public String getValue(){
            return text.getText();
        }
        
        //Get the coordinates of the text
        public double getCenterX(){
            return getTranslateX() + 100;
        }

        public double getCenterY(){
            return getTranslateY() + 100;
        }
    }
    
    private void setPlayable(){
        //Combinations go here
        int x,y;
        //For Horizontal Combinations
        for(x = 0; x < 3; x++){
            if(board[x][0].getValue()!="" 
                    && board[x][0].getValue().equals(board[x][1].getValue())
                    && board[x][0].getValue().equals(board[x][2].getValue())){
                playable = false;
                sx = x; 
                sy = 0;
                ex = x;
                ey = 2;
                break;
            }
        }
        //For Vetical Combinations
        for(y = 0; y < 3; y++){
            if(board[0][y].getValue()!=""
                    &&board[0][y].getValue().equals(board[1][y].getValue())
                    && board[0][y].getValue().equals(board[2][y].getValue())){
                playable = false;
                sx = 0;
                sy = y;
                ex = 2;
                ey = y;
                break;
            }
        }
        //For Diagonal Combinations
        if(board[0][0].getValue()!=""
                &&board[0][0].getValue().equals(board[1][1].getValue())
                && board[0][0].getValue().equals(board[2][2].getValue())){
            playable = false;
            sx = 0;
            sy = 0;
            ex = 2;
            ey = 2;
        }
        if(board[0][2].getValue()!=""
                &&board[0][2].getValue().equals(board[1][1].getValue())
                && board[0][2].getValue().equals(board[2][0].getValue())){
            playable = false;
            sx = 0;
            sy = 2;
            ex = 2;
            ey = 0;
        }
        if(!playable){
            String winVal = board[sx][sy].getValue();
            strikeAnim();
            Announce.winner(winVal);
            turn.setText("Game Over!");
        }
    }
    
    //Creating Stike Animation Method
    private void strikeAnim(){
        //Creating Object of class
        line = new Line();
        
        //Setting Start Points of Line
        line.setStartX(board[sx][sy].getCenterX());
        line.setStartY(board[sx][sy].getCenterY());
        
        //Setting End Points of Line
        line.setEndX(board[sx][sy].getCenterX());
        line.setEndY(board[sx][sy].getCenterY());
        
        //Adding Line to Pane Layout
        root.getChildren().add(line);
        
        //Timeline Animation Duration: 1 Second
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),
                new KeyValue(line.endXProperty(), board[ex][ey].getCenterX()),
                new KeyValue(line.endYProperty(), board[ex][ey].getCenterY())));
        timeline.play();
    }
    
    //Creating Game Scene Method
    private Parent createGameScene(){
        //Set layout size
        game_layout.setPrefSize(600,700);
        
        //Label for showing players turn
        turn = new Label("X's Turn");
        turn.setPrefHeight(100);
        turn.setPrefWidth(500);
        turn.setFont(Font.font("Verdana",40));
        
        //Reset Button
        Button reset = new Button("Reset");
        reset.setTranslateY(50);
        reset.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                for(int y = 0; y < 3; y++){
                    for(int x = 0; x < 3; x++){
                        board[x][y].text.setText("");
                    }
                } 
                line.setEndX(line.getStartX());
                line.setEndY(line.getStartY());
                turn.setText("X's Turn!");
                playable = true;
                turnX=true;
            }
        });
        
        //Horiontal Tab
        HBox info_tab = new HBox();
        info_tab.getChildren().addAll(turn,reset);
        
        //For creating the Nine Boxes
        for(int y = 0; y < 3; y++){
            for(int x = 0; x<3; x++){
                //Creating Box Object which gives a call to constructor
                Box box = new Box();

                //Set Positions For Boxes
                box.setTranslateX(x*200);
                box.setTranslateY(y*200);

                //Setting Layout for Boxes
                root.getChildren().add(box);

                //Linking Box With Board
                board[x][y] = box;
            }
        }
        game_layout.getChildren().addAll(info_tab,root);
        
        return game_layout;
    }
    
    public static void main(String args[]){
        launch(args);
    }
}
