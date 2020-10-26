import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	private JLabel label;
	private Image head_img;
	private Image body_img;
	private Image apple_img;
	private Timer timer;
	
	Snake snake = new Snake();
	Apple apple = new Apple();
	
	private String keyPressed = "right";
	private int score = 0;
	private boolean isApple = true;
	private JButton button;
	
	private final int LEFT_BORDER = 50;
	private final int RIGHT_BORDER = 546;
	private final int UP_BORDER = 50;
	private final int DOWN_BORDER = 329;
	
	public Game() {
		super.setLayout(null);
		super.setDoubleBuffered(true);
		
	
		label = new JLabel("Score: 0");
		label.setForeground(Color.WHITE);
		label.setBounds(290, 15, 100, 50);
		super.add(label);
		
		button = new JButton("Restart(R)");
		button.setFocusable(false);
		button.setRolloverEnabled(false);
		button.setBounds(270, 370, 100, 20);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				restart();
				
			}
			
	
		});
		super.add(button);
		
		timer = new Timer(150, this);
		timer.start();
		label.setFocusable(true);
		label.addKeyListener(new KeyListener() {
		    public void keyPressed(KeyEvent e) { 
		    
		    int key = e.getKeyCode();

		    if (key == KeyEvent.VK_LEFT) {
		        keyPressed="left";
		    }

		    if (key == KeyEvent.VK_RIGHT) {
		    	keyPressed="right";
		    }

		    if (key == KeyEvent.VK_UP) {
		    	keyPressed="up";
		    }

		    if (key == KeyEvent.VK_DOWN) {
		    	keyPressed="down";
		    }
		    
		    if (key == KeyEvent.VK_R) {
		    	restart();
		    }
		    
		    }

		    public void keyReleased(KeyEvent e) { /* ... */ }

		    public void keyTyped(KeyEvent e) { /* ... */ }
		});
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Color lightgreen = new Color(18,250,49);
		ImageIcon ii = new ImageIcon(this.getClass().getResource("head.png"));
		head_img = ii.getImage();
		ImageIcon ii2 = new ImageIcon(this.getClass().getResource("body.png"));
		body_img = ii2.getImage();
		ImageIcon ii3 = new ImageIcon(this.getClass().getResource("apple.png"));
		apple_img = ii3.getImage();
		
		g2d.fillRect(0, 0, 640, 480); 	
		g2d.setColor(lightgreen);
		g2d.fillRect(50, 50, 527, 310); 
		for(int i=snake.length;i>0;i--) {
		g2d.drawImage(body_img, snake.tail.get(i).getX(), snake.tail.get(i).getY(), this);
		}
		g2d.drawImage(head_img, snake.tail.get(0).getX(), snake.tail.get(0).getY(), this);
		g2d.drawImage(apple_img, apple.apple_pos.getX(), apple.apple_pos.getY(), this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		snake.move(keyPressed);
		detectColision();
		CheckApple();
		repaint();
		
	}
	
	private void detectColision() {
		if(
			snake.tail.get(0).getX() < LEFT_BORDER  || 
			snake.tail.get(0).getX() > RIGHT_BORDER || 
			snake.tail.get(0).getY() < UP_BORDER    || 
			snake.tail.get(0).getY() > DOWN_BORDER
		) { 
		timer.stop();
		}
		
		for(int i=1;i<=snake.length;i++) {
			if(
				snake.tail.get(0).getX() == snake.tail.get(i).getX() &&
				snake.tail.get(0).getY() == snake.tail.get(i).getY()
					){
				timer.stop();
			}
		}
	}
	
	public void restart() {
		snake.tail.get(0).setPos(snake.start_position.getX(), snake.start_position.getY());
		snake.length = 2;
		score = 0;
		refreshScore(score);
		keyPressed = "right";
		timer.start();
	}
	
	private void CheckApple() {
		
		if(snake.tail.get(0).getX() == apple.apple_pos.getX() && snake.tail.get(0).getY() == apple.apple_pos.getY()) {
			isApple=false;
			snake.eat();
			refreshScore(++score);
		}
		newApple();
		
	}
	
	private void newApple() {
		if(isApple == false) {
			apple.resetApple();
			while(isOnSnake(apple.apple_pos.getX(), apple.apple_pos.getY())) {
			apple.resetApple();
			}
			isApple=true;
		}
	}
	
	private void refreshScore(int score) {
		label.setText("Score: " + score);
	}
	
	private boolean isOnSnake(int x, int y) {
		for(int i = 0;i < snake.length;i++) {
			if(x == snake.tail.get(i).getX() && y == snake.tail.get(i).getY()) {
				return true;
			}
		}
		return false;
	}
			
}
