import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

/*need the Applet methods and the MouseMotionListener interface for the human controlled paddle*/
public class pongMain extends Applet implements MouseMotionListener, ActionListener, KeyListener {
	
	
	private static final long serialVersionUID = 1L;
	//Declare instances of the game pieces
	Ball ball;
	PaddleLeft pLeft;
	PaddleRight pRight;
	
	//Font used to display the score
	Font newFont = new Font("sansserif", Font.BOLD, 20);
	
	//Double buffered image to avoid flicker on repaint
	Graphics bufferGraphics; 
	
	//The Image will contain everything that has been drawn on bufferGraphics
	Image offscreen;
	
	//Variables used to set the width and height of the applet
	final int WIDTH = 500, HEIGHT = 300;
	
	//Variable used to record the time of the game
	long currentTime;

	//Decide if the computer paddle should move up or down
	int upOrDown;

	//Boolean to end the game
	boolean endGame = false;
	
	
	/*Init method used to initialize the applet*/
	public void init() {
		//Set the size of the applet
		setSize(WIDTH, HEIGHT);
		//Create pieces
		ball = new Ball();
		pLeft = new PaddleLeft();
		//The computer paddle will be set to the Y position of the ball minus 35 to be centered with the 70px long paddle
		pRight = new PaddleRight(ball.getY() - 35);
		
		//Add mouse motion listener so the user can control paddle
		addMouseMotionListener(this);
		
		//Color of the background
		setBackground(Color.BLACK);
		
		//Create an offscreen image to draw on
		offscreen = createImage(WIDTH, HEIGHT);
		bufferGraphics = offscreen.getGraphics();


	}
	
	/*Game Loop*/
	public void start() {
		
		//Use a timer to do a certain list of tasks every 15 milliseconds which is 1000/15 = 67 FPS (Uses swing timer as import)
		Timer time = new Timer(15, this);
		
		//This is the game loop which ends when the computer has scored 10 times
		time.start();
		//End the game with escape
		while (endGame == false) {
			//Nothing happens in here but this keeps the timer moving 
		}
		time.stop();
		
		//Repaint with the time that the player has laster
		repaint();
	}
	
	public void stop() {}
	
	/*Every 15 milliseconds, the Timer time triggers the actionPerformed method*/
	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Move the ball
		ball.move();
		
		//Line the computer paddle up with the ball
		upOrDown = 0;
		if (ball.getY() > pRight.getPos() + 35) {
			upOrDown = 5;
		}
		else if (ball.getY() < (pRight.getPos() + 35)) {
			upOrDown = -5;
		}

		pRight.setPos(pRight.getPos() + upOrDown);

		
		//Check ball for collision
		checkCollision();
		
		//Calls Paint method
		repaint();
	}
	
	
	public void checkCollision() {
		//Ball is 10*10 and x,y are the top left corner of the ball
		
		//Y Coordinate
		//If the top left corner y position is 0 or 290 we reverse the y- direction by multiplying ball.dy by -1
		if(ball.getY() <= 0 || ball.getY() >= 290) {
			ball.dy = (ball.dy * -1);
		}
		
		//Left Paddle Hit
		//If the ball is at the right-hand edge of the human paddle and the boolean hitPaddle() is true we reverse the dx of the ball
		if ((ball.getX() == 40) && hitLeftPaddle()) {
			if (ball.getY() > pLeft.getPos() - 35 && ball.getY() < pLeft.getPos() - 16) {
				ball.dy = ball.dy + 2;
			}
			else if (ball.getY() > pLeft.getPos() - -16) {
				ball.dy = ball.dy + 3;
			}
			else if (ball.getY() < pLeft.getPos() - -35 && ball.getY() > pLeft.getPos() - 51) {
				ball.dy = ball.dy - 2;
			}
			else if (ball.getY() < pLeft.getPos() - 52) {
				ball.dy = ball.dy - 3;
			}
			ball.dx = (ball.dx * -1);
		}
		
		//Computer paddle hit (computer cannot miss)
		if (ball.getX() == 460 && hitRightPaddle()) {
			ball.dx = (ball.dx * -1);
		}
		
		//Human paddle missed
		//Reset the ball and increment the score
		if (ball.getX() == 0) {
			pRight.setScore(pRight.getScore() + 1);
			ball.reset();
		}

		//Computer Paddle missed
		if (ball.getX() >= 500) {
			pLeft.setScore(pLeft.getScore() + 1);
			pRight.setPos(0);
			ball.reset();
		}
	}
	
	//Check to see if the ball hit the human paddle between the top and bottom right hand corners 
	public boolean hitLeftPaddle() {
		boolean didHit = false;
				
		if ((pLeft.getPos() - 10) <= ball.getY() && (pLeft.getPos() + 70) > ball.getY()) {
			didHit = true;
		}
		return didHit;
	}

	//Check to see if the ball hit the computer paddle between the top and bottom left hand corners
	public boolean hitRightPaddle() {
		boolean rightDidHit = false;
				
		if ((pRight.getPos() ) <= ball.getY() && (pRight.getPos() + 70) > ball.getY()) {
			rightDidHit = true;
		}
		return rightDidHit;
	}
	
	
	/*Use Buffered Graphics to draw onto off-screen image*/
	public void paint (Graphics g) {

		//Clear previous image
		bufferGraphics.clearRect(0, 0, WIDTH, HEIGHT);
		
		//Draw two paddles in blue
		bufferGraphics.setColor(Color.blue);
		
		//Make the paddles 10 * 70
		//Left (final, moving y position, width, height)
		bufferGraphics.fillRect(pLeft.XPOS, pLeft.getPos(), 10, 70);
		//Right
		bufferGraphics.fillRect(pRight.XPOS, pRight.getPos(), 10, 70);
		
		//Draw the mid-court line and score in white
		bufferGraphics.setColor(Color.white);
		bufferGraphics.setFont(newFont);
		
		//Show the player's hopeless circumstances
		bufferGraphics.drawString("" + pLeft.getScore(), 150, 15);
		
		//Get the score from the right paddle
		bufferGraphics.drawString("" + pRight.getScore(), 300, 15);
		
		//Mid court divider
		bufferGraphics.fillRect(240, 0, 20, 300);
		
		
		//Repainted one last time after the computer's score reached 10
		if (pRight.getScore() == 5) {
			//Divide milliseconds by 1000 to calculate how long the game lasted
			bufferGraphics.setColor(Color.blue);
			bufferGraphics.drawString("You Lose" , 30, 150);
		}

		//Repainted one last time after the computer's score reached 10
		if (pLeft.getScore() == 5) {
			//Divide milliseconds by 1000 to calculate how long the game lasted
			bufferGraphics.setColor(Color.blue);
			bufferGraphics.drawString("You Win!" , 30, 150);
		}
		
		
		//Draw the ball
		bufferGraphics.setColor(Color.red);
		bufferGraphics.fillRect(ball.getX(), ball.getY(), 10, 10);
		
		
		//Finally draw the off-screen image to the applet. Use g instead of bufferGraphics
		//Draw to offscreen at 0,0 from this instance
		g.drawImage(offscreen, 0, 0, this);

		//Create Key Listener
		this.addKeyListener(this);
		
		
		//This final line makes sure that all the monitors are up to date before proceeding
		Toolkit.getDefaultToolkit().sync();
	}
	
	
	//All good double buffers need this method or else the flickering will still occur
	public void update(Graphics g) {
		paint(g);
	}
	
	
	
	//This sets the mouse's y position to the center of the paddle
	public void mouseMoved(MouseEvent evt) {
		pLeft.setPos(evt.getY() - 35);
	}
	
	public void mouseDragged(MouseEvent evt) {
		//This method is necessary even though we do not use it
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			pLeft.setScore(0);
			pRight.setScore(0);
			ball.reset();
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			endGame = true;
		}
		
	}





	//@Override
	public void keyReleased(KeyEvent arg0) {
		
	}





	//@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}



