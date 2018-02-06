/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Jovana
 */


public class FlappyBird extends Application {
    //Ovo ti je oono sto vidis i to :D
    public static Pane gameRoot = new Pane();
    public static Pane appRoot = new Pane();
    public static Scene scene;
    
    ///Niz zidova onih
    public static ArrayList<Wall> walls = new ArrayList<>();
    //rezultat
    public static int score = 0;
    
    static AnimationTimer timer;

    public static void gameOver() {
        Label l = new Label("IGRA GOTOVA\n Vas rezultat je : " + score);
        timer.stop();
        Stage stage = new Stage();
        StackPane root = new StackPane();
        root.getChildren().add(l);
        bird.setOnMouseClicked(e->{});
        
        HBox hb = new HBox(15);
        Button newGame = new Button("Nova igra");
        Button exit = new Button("Izlaz");
        newGame.setOnMouseClicked(e -> {
            restart();
            stage.close();
            bird.v = new Point2D(0, 0);
            timer.start();
        });
        
        exit.setOnMouseClicked(e -> {
            stage.close();
        });
        
        hb.getChildren().addAll(newGame, exit);
        root.getChildren().add(hb);
        Scene scene1 = new Scene(root, 300, 300);
        stage.setScene(scene1);
        stage.show();
        stage.setResizable(false);
        
    }
    public static void restart(){
        score = 0;
        bird.setTranslateX(100);
        bird.setTranslateY(300);
        gameRoot.setLayoutX(0);
        scene.setOnMouseClicked(e->bird.jump());
    }
    public Label scoreLabel = new Label("Rezultat" + score);
    
    public static Bird bird = new Bird();
    
    public Parent createContent(){
        gameRoot.setPrefSize(600, 600);
        appRoot.getChildren().add(scoreLabel);
        
        for (int i = 0; i < 1000; i++) {
            //enter je prolaz kojim ptica treba da prodje
            
            int enter = (int)(Math.random()*100 + 100);
            //visina gornjeg zida 
            int height = new Random().nextInt(600 - enter);
            //gornji zzid
            Wall wall = new Wall(height);
            //pomjeren ramo daleko :D
            wall.setTranslateX(600 + i*350);
            wall.setTranslateY(0);
            //dodaje se u listu zidova
            walls.add(wall);
            //Donji zid
            Wall wall2 = new Wall(600 - enter - height);
            
            wall2.setTranslateX(600 + i*350);
            //On je od gornje margine spusten visina gornjeg zida plus prolaz
            wall2.setTranslateY(height + enter);
            walls.add(wall2);
            
            gameRoot.getChildren().addAll(wall, wall2);
        }
        
       gameRoot.getChildren().add(bird);
        
        appRoot.getChildren().add(gameRoot);
        appRoot.setStyle("-fx-background:skyblue");
        return appRoot;
    }
    
    public void update(){
        
        if (bird.v.getY() < 5) {
            bird.v = bird.v.add(0,1);    
        }
        
        bird.moveX((int)bird.v.getX());
        bird.moveY((int)bird.v.getY());
        
        scoreLabel.setText("Rezultat: " + score);
        //Ovo pomjera ekran zajedno sa pticom
        bird.translateXProperty().addListener((obs, old, newValue)->{
            int offset = newValue.intValue();
            
            if (offset > 200) {
                gameRoot.setLayoutX(-(offset - 200));
            }
        });
    }
    //Znaci svaka klasa koja je izvedena iz klase Application mora da implementira ovaj metod start
    public void start(Stage primaryStage) {
        //Ovde se dodaje panel koji smo napunili zidovima i pticom gore
        scene = new Scene(createContent());
        
        scene.setOnMouseClicked(e->bird.jump());
        
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        //Ovde onaj animacioni timer radi ovu metodu update
        timer = new AnimationTimer() {
            
            public void handle(long now) {
                update();
            }
        };
        
        timer.start();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
