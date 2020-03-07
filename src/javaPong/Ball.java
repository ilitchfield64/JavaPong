package javaPong;

import java.awt.Rectangle;
import java.util.Random;


public class Ball extends Rectangle{

    private Random Gen = new Random();
    private int ballVelocity = 0;
    private boolean ballIsMoving = false;
    boolean moveLeft = false, moveRight = false, moveUp = false, moveDown = false;
    int playerServing;
    
    public Ball(int x, int y, int width, int height,int playerThatScored){
        super(x,y,width,height);


        this.playerServing = playerThatScored;

        if(this.playerServing == 1){
            
        }else if (this.playerServing == 2){
            
        }else{
            if(Gen.nextInt(11) <= 5){
                moveRight = true;
                moveLeft = false;
            }else{
                moveRight=false;
                moveLeft=true;
                
            }
        }
    }  
    public void ballMove(int ballVelocity,boolean ballServed){
        if(ballServed && ballVelocity==0){
            ballVelocity=4;
            if(moveRight){ // ball will move right
                
                this.x += ballVelocity;
            }else if (moveLeft){ // ball will move left
               
                this.x -= ballVelocity;
            }
        
        }
        if (ballIsMoving) {
            if(moveRight){ // ball will move right
                
                this.x += ballVelocity;
            }else if (moveLeft){ // ball will move left
               
                this.x -= ballVelocity;
            }
        }
    }
    
}