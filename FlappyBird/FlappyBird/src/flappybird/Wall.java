/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flappybird;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author rade
 */
public class Wall extends Pane{
    
    Rectangle r;
        public int height;

    public Wall(int height){
        r = new Rectangle(20, height);
        this.height = height;
        
        getChildren().add(r);
        
    }
    
    
}
