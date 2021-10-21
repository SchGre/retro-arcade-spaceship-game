import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public GameFrame() 
	{
		this.add(new GamePanel());
		this.setTitle("retro-arcade-spaceship-game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) 
	{
		new GameFrame();
	}

}
