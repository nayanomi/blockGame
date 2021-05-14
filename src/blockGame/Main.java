package blockGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main {
	static JFrame blockGame;
	static int BLOCK_WIDTH = 40;
	static int BLOCK_HEIGHT = 15;
	
	static int CANVAS_WIDTH = 434;
	static int CANVAS_HEIGHT = 600;
	static int COLS = 10;
	static int ROWS = 5;
	
	static int BAR_WIDTH = 100;
	static int BAR_HEIGHT = 15;
	
	static int BALL_WIDTH = 15;
	static int BALL_HEIGHT = 15;
	static int BALL_SPEED = 5;
	
	static Block[][] blocks = new Block[COLS][ROWS];
	
	static MyPanel myPanel = null;
	
	static Bar myBar = new Bar();
	static Ball myBall = new Ball();
	static JButton startBtn = new JButton("START");;
	
	static int dir = 1; //ball방향
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		blockGame = new MyFrame("Block Game");
		
	}
	
	static class MyFrame extends JFrame {
		public MyFrame(String title) {
			super(title);
			
			this.setVisible(true);
			this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
			this.setLocation(400, 300);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			myPanel = new MyPanel();
			this.add("Center", myPanel);
			this.add(startBtn);
			
			initData();
			
			
		}
		
		public void initData() {
			
			for(int i=0; i<COLS; i++) {
				for(int k=0;k<ROWS;k++) {
					blocks[i][k] = new Block();
					blocks[i][k].x = i*BLOCK_WIDTH+(2*i);
					blocks[i][k].y = k*BLOCK_HEIGHT+(2*k)+30;
					blocks[i][k].width = BLOCK_WIDTH;
					blocks[i][k].height = BLOCK_HEIGHT;
					if(k==0)blocks[i][k].color = Color.red;
					if(k==1)blocks[i][k].color = Color.gray;
					if(k==2)blocks[i][k].color = Color.yellow;
					if(k==3)blocks[i][k].color = Color.GREEN;
					if(k==4)blocks[i][k].color = Color.blue;
					
				}
			}
			
			startBtn.setSize(100, 30);
			startBtn.setVisible(true);
			startBtn.setBounds(CANVAS_WIDTH/2 - 50, CANVAS_HEIGHT/2-50, 100,50);
			startBtn.addActionListener(new ActionListener() {
				 
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                startTimer();
	                setKeyListener();
	                startBtn.setVisible(false);
	                
	            }
			});
			
		}
		
		
		public void setKeyListener() {
			this.addKeyListener( new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					System.out.println(e.getKeyCode());
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						myBar.x -= 10;
					}else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						myBar.x += 10;
					}
					
				}
			});
		}
		
		//게임시작
		public void startTimer() {
			
			Timer timer = new Timer(20, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {System.out.println(e.getSource());
					movement();
					checkDir();
					myPanel.repaint();
					
				}
			});
			
			timer.start();
		}
		
		public void movement() {
			if(dir == 1) { // Up Right
				myBall.x += BALL_SPEED;
				myBall.y -= BALL_SPEED;
				
			}else if(dir == 2) { // Down Rright
				myBall.x += BALL_SPEED;
				myBall.y += BALL_SPEED;
			}else if(dir == 3) { //Ddown Left
				myBall.x -= BALL_SPEED;
				myBall.y += BALL_SPEED;
			}else if(dir == 4) { //Up Left
				myBall.x -= BALL_SPEED;
				myBall.y -= BALL_SPEED;
			}
			
			
		}
		
		//두 사각형이 겹치는지 체크하는 함수
		public boolean dupRect(Rectangle rect1, Rectangle rect2) {
			
			return rect1.intersects(rect2);
		}
		
		
		public void checkDir() {
			if(dir == 1) { // Up Right
				if(myBall.x >= CANVAS_WIDTH-BALL_WIDTH) {
					dir = 4;
				}
				if(myBall.y <= 0) {
					dir = 2;
				}
			}else if(dir == 2) { // Down Rright
				if(myBall.x+myBall.width >= CANVAS_WIDTH ) {
					dir = 3;
				}
				if(dupRect(new Rectangle(myBall.x, myBall.y, myBall.width, myBall.height), new Rectangle(myBar.x, myBar.y, myBar.width, myBar.height))) {
					dir = 1;
				}
			}else if(dir == 3) { //Ddown Left
				if(myBall.x <= 0) {
					dir = 2;
				}
				if(dupRect(new Rectangle(myBall.x, myBall.y, myBall.width, myBall.height), new Rectangle(myBar.x, myBar.y, myBar.width, myBar.height))) {
					dir = 4;
				}
			}else if(dir == 4) { //Up Left
				if(myBall.x <= 0) {
					dir = 1;
				}
				if(myBall.y <= 0) {
					dir = 3;
				}
			}
			
			
		}
	}
	
	public static class MyPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public MyPanel() {
			
			this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
			this.setBackground(Color.black);
			this.setLayout(null);
		}
		
		
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			
			Graphics2D g2d = (Graphics2D)g;
			
			drawUI(g2d);
		}
		
		private void drawUI(Graphics2D g2d) {
			
			for(int i=0; i<COLS; i++) {
				for(int k=0;k<ROWS;k++) {
					g2d.setColor(blocks[i][k].color);
					g2d.fillRect(blocks[i][k].x, blocks[i][k].y, blocks[i][k].width, blocks[i][k].height);
				}
				
			}
			g2d.setColor(myBar.color);
			g2d.fillRect(myBar.x, myBar.y, BAR_WIDTH, BAR_HEIGHT);
			
			g2d.setColor(myBall.color);
			g2d.fillOval(myBall.x, myBall.y, BALL_WIDTH, BALL_HEIGHT);
			
			
			
		}
		
	}
	
	static class Block {
		int width;
		int height;
		int x;
		int y;
		Color color;

	}
	
	static class Bar {
		int width = BAR_WIDTH;
		int height = BAR_HEIGHT;
		int x = CANVAS_WIDTH/2 - width/2;
		int y = CANVAS_HEIGHT - 100;
		Color color = Color.white;
	}
	
	static class Ball {
		int width = BALL_WIDTH;
		int height = BALL_HEIGHT;
		int x = CANVAS_WIDTH/2 - width/2;
		int y = myBar.y - height;
		Color color = Color.white;
		
		Point getCenter() {
			return new Point(myBall.x + BALL_WIDTH/2, myBall.y + BALL_HEIGHT/2);
		}
		Point getTop() {
			return new Point(myBall.x + BALL_WIDTH/2, myBall.y);
		}
		Point getBottom() {
			return new Point(myBall.x + BALL_WIDTH/2, myBall.y + BALL_HEIGHT);
		}
		Point getRight() {
			return new Point(myBall.x + BALL_WIDTH, myBall.y + BALL_HEIGHT/2);
		}
		Point getLeft() {
			return new Point(myBall.x, myBall.y + BALL_HEIGHT/2);
		}
	}
	
}



