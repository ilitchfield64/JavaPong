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
    boolean pladdleUp = false, paddleDown = false, paddleLeft = false, paddleRight = false;
    boolean otherUp = false, otherDown = false, otherLeft = false, otherRight = false;
    
    boolean ballServed = false; // is the ball moving?
    boolean ballLeft = false; 
    boolean ballRight = false;
    boolean ballUp = false;
    boolean ballDown = false;
    boolean ballVerticalDirection = false; //false will send the ball up, true will send the ball down
    // Objects
    Paddle player1;
    Rectangle otherPaddle;
    Rectangle defaultBall;
    Ball ball;
    
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
        player1 = new Paddle(SCREEN_WIDTH / 16,(SCREEN_HEIGHT/2)- (SCREEN_HEIGHT/8),SCREEN_WIDTH/28,SCREEN_HEIGHT/ 6);
        otherPaddle = new Rectangle(SCREEN_WIDTH-(SCREEN_WIDTH / 10),(SCREEN_HEIGHT/2)- (SCREEN_HEIGHT/8),SCREEN_WIDTH/28,SCREEN_HEIGHT/ 6);
        defaultBall = new Rectangle(SCREEN_WIDTH/2, SCREEN_HEIGHT/2,SCREEN_WIDTH/48,SCREEN_WIDTH/48);
        ball = new Ball(SCREEN_WIDTH/2, SCREEN_HEIGHT/2,SCREEN_WIDTH/48,SCREEN_WIDTH/48, 0);

    }
    
// Movement of objects
    //Paddle Movement

        public void paddleMove() { // This method will handle movement speed

        if (paddleLeft) { // This moves player left
            // System.out.println("Left");
            player1.x = player1.x - moveSpeed;
        }
        if (paddleRight) { // This moves player right
            //  System.out.println("Right");
            player1.x = player1.x + moveSpeed;

        }
        
        if (pladdleUp) { // This moves player left
            // System.out.println("Left");
            player1.y = player1.y - moveSpeed;
        }
        if (paddleDown) { // This moves player right
            //  System.out.println("Right");
            player1.y = player1.y + moveSpeed;

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

    // Will decide velocity of the ball
    public int ballSpeed(){
        return(0);
    }
    // Will decide direction and speed based on impact coords
    public void ballHitPaddle(Rectangle paddle){
        if(ball.intersects(paddle)){
            
        }
    }
    // Will decide @ what speed and angle the ball should bounce off a wall
    public void ballHitWall(){
        
    }
    // Will score on the approriate player
    public void ballOut(){
       if (ball.x < -25 ){
           ++player2Score;
           ballRight = false;
           ballLeft=true;
           ballServed=false;
           ball.x = defaultBall.x;
           ball.y = defaultBall.y;
       }
       if (ball.x > (SCREEN_WIDTH+25)){
           ++player1Score;
           ballLeft= false;
           ballRight = true;
           ballServed = false;
           ball.x = defaultBall.x;
           ball.y = defaultBall.y;
       }
    }
    
    // Prevents the objects from moving vertically off the screen
    public void Borders(Rectangle b) {

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
        paddleMove();
        Borders(player1);
        otherPaddleMovement();
        Borders(otherPaddle);
        ball.ballMove(ballVelocity,ballServed);
        Borders(ball);
        ballOut();
    }
        public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.setColor(Color.WHITE);
        g.fillRect(player1.x, player1.y, player1.width, player1.height);
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
                pladdleUp = true;
                break;
            case KeyEvent.VK_S:
                paddleDown = true;
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
                pladdleUp = false;
                break;
            case KeyEvent.VK_S:
                paddleDown = false;
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
