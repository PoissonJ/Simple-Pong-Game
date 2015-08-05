/* Ball class */
public class Ball {
	
	private int xPos, yPos;
	//Upper left hand of the applet is (0,0) putting the applet in quadrant IV. Hence why dy is negative
	public int dx = 5, dy = -5;
	
	public Ball() {
		//Sets the initial ball position near the center of the screen
		setPos(250, 140);
	}
	
	public void setPos(int x, int y) {
		this.xPos = x;
		this.yPos = y;
	}
	
	public int getX() {
		return xPos;
	}
	
	public int getY() {
		return yPos;
	}
	
	//Method to move the ball
	public void move() {
		//Takes the current x and y direction and then adds their current direction of movement to them
		setPos(this.getX() + dx, this.getY() + dy);
	}
	
	//Reset method for when the computer scores
	public void reset() {
		//Same as initial setup
		setPos(250, 140);
		dx = 5;
		dy = -5;
	}
}
