/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram 
{

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 700;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = 500 ;
	private static final int HEIGHT = 500;
	
	private static final int FRAME_WIDTH_OFFSET = (APPLICATION_WIDTH - WIDTH)/2 ;
	private static final int FRAME_HEIGHT_OFFSET = (APPLICATION_HEIGHT - HEIGHT)/2;
	
/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;
	
	private static final int BRICK_COUNT = NBRICKS_PER_ROW*NBRICK_ROWS;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 10;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 5;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int DELAY = 1;
	 		
	private GameCounter counter;
	
	private Ball ball;

	private BrickFactory factory;
	
	private GObject paddle;
	
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	private int level;
	
	
	private void gameSetup() 
	{
		counter = new GameCounter();
	}
	// для уже засетапленой игры для сетапа уровня сложности 
	private void levelSetup(int level) {
		removeAll();
		this.level = level;
		this.counter.nextLevel();
		factory = new BrickFactory(this);
		factory.createTable(
					NBRICK_ROWS, NBRICKS_PER_ROW, BRICK_WIDTH, BRICK_HEIGHT, BRICK_SEP,
					FRAME_WIDTH_OFFSET, FRAME_HEIGHT_OFFSET + BRICK_HEIGHT*4
				);
		createPuddle();
		createBall();
		drawFrame();
		
	}
	
	private void createBall() {
		double vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) vx = -vx;
		
		Ball ball = new Ball(BALL_RADIUS,  vx, this.level);
		
		this.ball = ball;
		
		add(ball, FRAME_WIDTH_OFFSET + paddle.getX()/2, -FRAME_HEIGHT_OFFSET + paddle.getY()-BALL_RADIUS*3);
		
		
	}
	// ракетка
	private void createPuddle()
	{
		// this.level нужен для длинны ракетки
		// Костыль, исправить
		paddle = factory.createBrick(FRAME_WIDTH_OFFSET, FRAME_HEIGHT_OFFSET + HEIGHT, WIDTH,  FRAME_HEIGHT_OFFSET + HEIGHT, Color.cyan);
	}
	
	
	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() 
	{
		setup();
		while(true) {
			while(true) {
				gameSetup();
				levelSetup(1);
				play();
				if(!counter.levelPassed()) {
					break;
				}
				levelSetup(2);
				play();
			}
			thanksForPlaing();
		}

	}
	private void thanksForPlaing() {
		if(counter.levelPassed()) {
			congrats();
		} else {
			tryAgain();
		}
		
	}
	//sound 
	private void tryAgain() 
	{
		
		
	}
	//sound 
	private void congrats() 
	{
		
		
	}
	// забей
	private void checkForCollisions() 
	{	
		if ((ball.getX() - ball.vx <= FRAME_WIDTH_OFFSET && ball.vx < 0 )
				||
				(ball.getX() + ball.vx >= (WIDTH+FRAME_WIDTH_OFFSET - BALL_RADIUS*2) 
				&& 
				ball.vx>0)) {
			ball.redirectX();
		}
		
		if ((ball.getY() - ball.vy <= FRAME_HEIGHT_OFFSET&& ball.vy < 0 )) {
			ball.redirectY();
		}
		
		GObject obj = getCollidingObject(ball);
		
		if (obj != null) {
			if(obj != paddle) {
				removeBrick(obj);
				ball.redirectY();
				if(ball.getY() < obj.getY()) {
					ball.redirectX();
				}
			} else {
				ball.redirectY();
				if(ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 && ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS*2 + 4) {
	                ball.redirectX();   
	            }
			}
			playSound();
		}
		
	}
	
	private void removeBrick(GObject obj) {
		remove(obj);
		counter.increaseScore();
		
	}
	private void playSound() {
		// TODO Auto-generated method stub
		
	}

	// забей
	private GObject getCollidingObject(GObject gobj) {
		GObject obj = getElementAt(gobj.getX(), gobj.getY());
		if(obj != null) {
			return obj;
		}
		obj = getElementAt(gobj.getX() + gobj.getWidth(), gobj.getY());
		if(obj != null) {
			return obj;
		}
		obj = getElementAt(gobj.getX(), gobj.getY() + gobj.getHeight());
		if(obj != null) {
			return obj;
		}
		
		obj = getElementAt(gobj.getX() + gobj.getWidth(), gobj.getY() + gobj.getHeight());
		if(obj != null) {
			return obj;
		}
		return null;
	}



	private void setup() 
	{
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		drawFrame();
		addMouseListeners();
		
	}
	// нарисовать фрейм для игры
	private void drawFrame() {
//		GRect frame = new GRect(WIDTH, HEIGHT);
//		this.frame = frame;
//		frame.setColor(Color.black);
//		frame.setFilled(true);
//		frame.setFillColor(Color.black);
//		add(frame, FRAME_WIDTH_OFFSET, FRAME_HEIGHT_OFFSET);
		
		
	}

	private void play() 
	{
		while(gameContinues()) {
			ball.shift();
			checkForCollisions(); 
			checkForLife();
			pause(DELAY); 
		}
		
	}
	
	private void checkForLife() {
		if(ball.getY() > HEIGHT+FRAME_HEIGHT_OFFSET ) {
			GameCounter.decreaseLife();
			createBall();
		}
		
	}



	private boolean gameContinues()
	{
		return 	counter.isAlive() 
				&& 
				(counter.getScore() < BRICK_COUNT);
	}
}
