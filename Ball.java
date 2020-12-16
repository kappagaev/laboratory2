import java.awt.Color;

import acm.graphics.GOval;

public class Ball extends GOval
{
	public double vx;
	
	public double vy;

	public Ball(double radius, double vx, double vy) 
	{
		super(radius*2, radius*2);
		this.vx = vx;
		this.vy = vy;
		this.setFilled(true);
		this.setColor(Color.black);
		this.setFillColor(Color.black);
	}

	public void shift() 
	{
		move(vx, vy);	
	}
	
	public void setVector(int x, int y)
	{
		this.vx = x;
		this.vy = y;
	}

	public void redirectX() {
		vx = -vx;
	}
	
	public void redirectY() {
		vy = -vy;
	}
	
	
}
