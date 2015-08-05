/*Human controlled paddle*/

public class PaddleLeft {
	private int leftScore;
	private int yPos = 0;
	final int XPOS = 30; //Width of the paddle
	
	public PaddleLeft() {
		//Set up the paddle to start at a y position of 120
		setPos(120);
	}
	
	public void setPos(int pos) {
		this.yPos = pos;
		if (yPos > 230) {
			setPos(230); //TODO: Mess with these to find out what they do
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
		this.leftScore = score;
	}
	public int getScore() {
		return this.leftScore;
	}
}
