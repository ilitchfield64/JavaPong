/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaPong;

import java.awt.Rectangle;
/**
 *
 * @author Ian
 */
public class Paddle extends Rectangle{        


    private int score = 0;
    private final int moveSpeed = 4;
    private int paddleID = 0;
    
    public Paddle(int x, int y, int width, int height){
        super(x,y,width,height);
        
        ++paddleID;
    }
        //Paddle Movement

        public void moveUp(boolean moveUp) { // This method will handle movement speed
        
        if (moveUp) { // This moves player left
            // System.out.println("up");
            this.y = this.y - moveSpeed;
        }
        }
        public void moveDown(boolean moveDown){
        if (moveDown) { // This moves player right
            //  System.out.println("down");
            this.y = this.y + moveSpeed;

        }
        }
        public int getID(){
            return paddleID;
        }


}