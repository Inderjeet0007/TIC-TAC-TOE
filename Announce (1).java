package tictactoe;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Announce extends Thread{
    public static void winner(String win){
        //Create a new Stage i.e. Dialog Box
        final Stage window = new Stage();
        window.setTitle("Game Over!");
        window.setMinWidth(500);
        //Setting Winner Text
        Text winner = new Text("Player " + win + " wins!");
        winner.setFill(Color.RED);
        winner.setFont(Font.font("Verdanaa", 15));
        //Making Layout for Alert Box
        VBox layout = new VBox(1);
        layout.getChildren().add(winner);
        layout.setAlignment(Pos.CENTER);
        //Setting the Scene
        Scene scene = new Scene(layout,250,50);
        window.setScene(scene);
        window.showAndWait();
    }
}
