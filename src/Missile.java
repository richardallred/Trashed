import java.awt.Image;

public class Missile {
	private double destX, destY;
	private double x, y, speed, direction;
	private Image missilePic = null;
	
	Missile(int initX, int initY, double destX, double destY, double speed){
		this.destX = destX;
		this.destY = destY;
		direction = Math.atan2(y-destY, x-destX);
		this.speed = speed;
		x = initX;
		y = initY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public Image getMissileImage(){
		return missilePic;
	}
	
	public void move(){
		this.x -= speed*Math.cos(direction);
		this.y -= speed*Math.sin(direction);
	}

	public boolean detectCollision(){
		if ((Math.abs(x-destX)+Math.abs(y-destY))<1) {
			return true;
		}
		return false;
	}
}
