/*The same as paddle left except it uses the y position of the ball to 
 * determine the paddle location instead of the mouse
 */
public class PaddleRight {
	private int yPos = 0;
	private int rightScore;
	final int XPOS = 460;
	
	//Constructor takes in an integer which is the y position of the ball
	public PaddleRight(int ballPos) {
		setPos(ballPos);
		setScore(0);
	}
	
	public void setPos(int yPos) {
		this.yPos = yPos;
		if (yPos > 230) {
			setPos(230);
		}
		else if (yPos < 0) {
			setPos(0);
		}
	}
	
	public int getPos() {
		return yPos;
	}
	
	//Setters and getters
	public void setScore(int score) {
		this.rightScore = score;
	}
	public int getScore() {
		return this.rightScore;
	}
}
