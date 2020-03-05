/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaPong;
// Window
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
// Content
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.*;
import javax.imageio.ImageIO;

//User Input
import java.util.Scanner;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

// System I/O
import java.io.File;
import java.io.IOException;
import java.net.URL;

// Math Functions
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 *
 * @author ilitc
 */
public class JavaPong extends JPanel implements KeyListener, MouseListener, MouseMotionListener{

// Declarations
    //CONSTANTS
    final int SCREEN_HEIGHT = 600;
    final int SCREEN_WIDTH = 800;
    
    // Game State Variables
    int player1Score = 0;
    int player2Score = 0;
    int moveSpeed = 4;
    int ballVelocity = 0;
    // How movement will be handled
    boolean playerUp = false, playerDown = false, playerLeft = false, playerRight = false;
    boolean otherUp = false, otherDown = false, otherLeft = false, otherRight = false;
    boolean ballServed = false; // is the ball moving?
    boolean ballHorizontalDirection = false; // false will send ball to the left, true will to the right
    boolean ballIsMovingVertical = false; // Is the ball moving vertically
    boolean ballVerticalDirection = false; //false will send the ball up, true will send the ball down
    // Objects
    Rectangle playerPaddle;
    Rectangle otherPaddle;
    Rectangle defaultBall;
    Rectangle ball;
    
    // Clock
    Clock updateClock = Clock.systemDefaultZone();
    Clock temp = Clock.offset(updateClock, Duration.ofMillis(1));
    Instant update = temp.instant();
    
    Random Gen = new Random();
    
    public JavaPong() {
        // Sets up the window
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setFocusable(true);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        // Creates the objects
        playerPaddle = new Rectangle(SCREEN_WIDTH / 16,(SCREEN_HEIGHT/2)- (SCREEN_HEIGHT/8),SCREEN_WIDTH/28,SCREEN_HEIGHT/ 6);
        otherPaddle = new Rectangle(SCREEN_WIDTH-(SCREEN_WIDTH / 10),(SCREEN_HEIGHT/2)- (SCREEN_HEIGHT/8),SCREEN_WIDTH/28,SCREEN_HEIGHT/ 6);
        defaultBall = new Rectangle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2,SCREEN_WIDTH/48,SCREEN_WIDTH/48);
        ball = new Rectangle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2,SCREEN_WIDTH/48,SCREEN_WIDTH/48);
        // Serves the ball in a Random Direction at runtime
        if(Gen.nextInt(11) <= 5){
            ballHorizontalDirection = true;
        }
        
    }
    
// Movement of objects
    //Paddle Movement
        public void playerMovement() { // This method will handle movement speed

        if (playerLeft) { // This moves player left
            // System.out.println("Left");
            playerPaddle.x = playerPaddle.x - moveSpeed;
        }
        if (playerRight) { // This moves player right
            //  System.out.println("Right");
            playerPaddle.x = playerPaddle.x + moveSpeed;

        }
        
        if (playerUp) { // This moves player left
            // System.out.println("Left");
            playerPaddle.y = playerPaddle.y - moveSpeed;
        }
        if (playerDown) { // This moves player right
            //  System.out.println("Right");
            playerPaddle.y = playerPaddle.y + moveSpeed;

        }
        //player.x += playerX;

    }
        
    public void otherPaddleMovement() { // This method will handle movement speed

        if (otherLeft) { // This moves player left
            // System.out.println("Left");
            otherPaddle.x = otherPaddle.x - moveSpeed;
        }
        if (otherRight) { // This moves player right
            //  System.out.println("Right");
            otherPaddle.x = otherPaddle.x + moveSpeed;

        }
        
        if (otherUp) { // This moves player left
            // System.out.println("Left");
            otherPaddle.y = otherPaddle.y - moveSpeed;
        }
        if (otherDown) { // This moves player right
            //  System.out.println("Right");
            otherPaddle.y = otherPaddle.y + moveSpeed;

        }

    }
    
// Ball Methods
    public void ballMove(){
        if(!ballServed && ballVelocity==0){
            ballVelocity=4;
            if(ballHorizontalDirection){ // ball will move right
                
                ball.x += ballVelocity;
            }else if (!ballHorizontalDirection){ // ball will move left
               
                ball.x -= ballVelocity;
            }
        
        }
        if (ballServed) {
            if(ballHorizontalDirection){ // ball will move right
                
                ball.x += ballVelocity;
            }else if (!ballHorizontalDirection){ // ball will move left
               
                ball.x -= ballVelocity;
            }
        }
    }
    // Will decide velocity of the ball
    public int ballSpeed(){
        return(0);
    }
    // Will decide direction and speed based on impact coords
    public void ballHitPaddle(){
        
    }
    // Will decide @ what speed and angle the ball should bounce off a wall
    public void ballHitWall(){
        
    }
    // Will score on the approriate player
    public void ballOut(){
       if (ball.x < -25 ){
           ++player2Score;
           ballHorizontalDirection = false;
           ballServed=false;
           ball.x = defaultBall.x;
           ball.y = defaultBall.y;
       }
       if (ball.x > (SCREEN_WIDTH+25)){
           ++player1Score;
           ballHorizontalDirection = true;
           ballServed = false;
           ball.x = defaultBall.x;
           ball.y = defaultBall.y;
       }
    }
    
    // Prevents the objects from moving vertically off the screen
    public void Boarders(Rectangle b) {

        if (b.y <= 0) {
            b.y += moveSpeed;
        }
        if (b.y >= (SCREEN_HEIGHT - (b.height - 1))) {
            b.y -= moveSpeed;
        }
        /*
        if (b.x <= 0) {
            b.x += moveSpeed;
        }
        if (b.x >= (SCREEN_WIDTH - (b.width - 1))) {
            b.x -= moveSpeed;
        }
        */
    }
    
    // Resets the software Clock
    public void resetUpdateClock() {
        temp = Clock.offset(updateClock, Duration.ofMillis(4));
        update = temp.instant();
        //clock2 = clock2.plusSeconds(2);
    }
    
    // Updates locations of the objects
    public void update(){
        playerMovement();
        Boarders(playerPaddle);
        otherPaddleMovement();
        Boarders(otherPaddle);
        ballMove();
        Boarders(ball);
        ballOut();
    }
        public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(playerPaddle.x, playerPaddle.y, playerPaddle.width, playerPaddle.height);
        g.fillRect(otherPaddle.x, otherPaddle.y, otherPaddle.width,otherPaddle.height);
        g.fillRect(ball.x, ball.y, ball.width, ball.height);
        
        if (updateClock.instant().compareTo(update) >= 0) { //updates clock cycle
            resetUpdateClock();
            update();
        }
        repaint();
    }
    public static void main(String[] args) {
        JavaPong game = new JavaPong();
        JFrame frame = new JFrame();
        frame.setTitle("Testing Graphics");
        frame.add(game);
        frame.pack();
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                playerUp = true;
                break;
            case KeyEvent.VK_S:
                playerDown = true;
                break;
            
            case KeyEvent.VK_UP:
                otherUp = true;
                
                break;    
            case KeyEvent.VK_DOWN:
                otherDown = true;
                break;
            case KeyEvent.VK_SPACE:
                ballServed = true;
                break;
            
        }
    }    

    @Override
    public void keyReleased(KeyEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                playerUp = false;
                break;
            case KeyEvent.VK_S:
                playerDown = false;
                break;
           
            case KeyEvent.VK_UP:
                otherUp = false;
                
                break;    
            case KeyEvent.VK_DOWN:
                otherDown = false;
                break;

            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
