
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;


public class GUI implements ActionListener {
	

	private JFrame frame;
	
	public GUI(){
		
		frame = new JFrame();
		frame.add(new Game());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Snake Game");
		frame.pack();
		frame.setSize(640, 480);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	
	}

}
