/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rade
 */
public class Bird extends Pane {
    
    Point2D v;
    ImageView r;

    public Bird(){
        Image i = null;
        Path p =Paths.get("flappybird.png");
        try {
            i = new Image(((p.toUri()).toURL()).toString());
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Bird.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        r = new ImageView(i);
        
        r.setFitHeight(50);
        r.setFitWidth(50);
        v = new Point2D(0, 0);
        
        
        setTranslateX(100);
        setTranslateY(300);
        getChildren().add(r);
    }
    
    
    void moveX(int value) {
        for (int i = 0; i < value; i++) {
            setTranslateX(getTranslateX()+1);
            for (Wall wall : FlappyBird.walls) {
                if (getBoundsInParent().intersects(wall.getBoundsInParent())) {
                    FlappyBird.timer.stop();
                    FlappyBird.gameOver();
                    return;
                }
                
                if (getTranslateX() == wall.getTranslateX()){
                    FlappyBird.score ++;
                    return;
                }
            }
            
        
        }
        
        
    }

    void moveY(int value) {
        boolean moveDown = (value > 0)?true:false;
        
        for (int i = 0; i < Math.abs(value); i++) {
            for (Wall wall : FlappyBird.walls) {
                
                if (this.getBoundsInParent().intersects(wall.getBoundsInParent())){
                    FlappyBird.timer.stop();
                    FlappyBird.gameOver();
                    return;
                    
                    
                    
                }
            }
        }
        
        if (getTranslateY() < 0){
            FlappyBird.timer.stop();
            FlappyBird.gameOver();
            return;
        }
        if (getTranslateY() > 550) {
            FlappyBird.timer.stop();
            FlappyBird.gameOver();
            return;
        }
        //Znaci, ako treba da ide dole na stari polozaj se dodaje 2, ako treba gore na stari polozaj se dodjae 1
        setTranslateY(getTranslateY() + (moveDown ? 2 : -1));
    }

    void jump() {
        v = new Point2D(1, 0);
        setTranslateY(getTranslateY() -60);
        setTranslateX(getTranslateX() + 5);
        
    }
    
}
