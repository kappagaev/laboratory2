import java.awt.Color;

import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class BrickFactory 
{
	
	private GraphicsProgram game;
	
	public BrickFactory(GraphicsProgram game)
	{
		this.game = game;
	}

	public void createTable(int rows, int bricksPerRow, int width, int height, int offset, int widthOffset, int heightOffset) {
		for (int i = 0; i < rows; i++) {
			for (int b = 0; b < bricksPerRow; b++) {
				int x = b * offset + offset/2 + b * width + widthOffset;
				int y = i * offset + offset/2 + i * height + heightOffset;
				Color color = getRowBrickColor(i, rows);
				createBrick(x, y, width, height, color);
				
			}
		}
	}
	
	private Color getRowBrickColor(int i, int rows) 
	{
		double steps = rows/5;
		if (i <= steps) {
			
			return Color.RED;
		} else if(i <= 2*steps) {
			
			return Color.ORANGE;
		}else if(i <= 3*steps) {
			
			return Color.YELLOW;
		} else if(i <= 4*steps) {
			
			return Color.GREEN;
		} else {
			
			return Color.CYAN;
		}
	}

	public GRect createBrick(int x, int y, int width, int height, Color color)
	{
		GRect brick = new GRect(width, height);
		brick.setFilled(true);
		brick.setColor(color);
		brick.setFillColor(color);
		game.add(brick, x, y);
		return brick;
	}
}
