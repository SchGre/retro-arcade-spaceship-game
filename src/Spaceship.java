import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spaceship {
	
	private Image sprite;
	
	private int health;
	private int speed;
	
	private int x;
	private int y;
	
	public Spaceship()
	{
		health = 3;
		speed = 10;
				
		try 
		{
			sprite = ImageIO.read(new File("resources/spaceship.png"));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		x = GamePanel.GAME_SCREEN_WIDTH / 2 - sprite.getWidth(null) / 2;
		y = GamePanel.GAME_SCREEN_HEIGHT - sprite.getHeight(null) - 10;
	}

	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}

	public int getSpeed() 
	{
		return speed;
	}

	public void setSpeed(int speed) 
	{
		this.speed = speed;
	}

	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public int getY() 
	{
		return y;
	}

	public void setY(int y) 
	{
		this.y = y;
	}
	
	public Image getImage()
	{
		return sprite;
	}
	
}
