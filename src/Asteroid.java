import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Asteroid {
	
	final static int speed = 5;
	
	private Image sprite;
	
	private int x;
	private int y;
	
	private Random random;
	
	public Asteroid()
	{
		try 
		{
			sprite = ImageIO.read(new File("resources/asteroid.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		random = new Random();
		this.setX(random.nextInt(GamePanel.GAME_SCREEN_WIDTH - sprite.getWidth(null)));
		this.setY(0);
	}

	public Image getImage()
	{
		return sprite;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}

}
