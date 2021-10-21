import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	
	static final int GAME_SCREEN_WIDTH = 500;
	static final int GAME_SCREEN_HEIGHT = 700;
	static final int DELAY = 25;
	private int astroDelay = 300;
	
	private Spaceship spaceship;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Laser> lasers;
	
	private boolean running = false;
	private boolean gameover = false;
	
	private Timer timer, astroTimer;
	
	private ArrayList<Integer> keys;
	
	public GamePanel()
	{
		this.setPreferredSize(new Dimension(GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT));
		this.addKeyListener(new MyKeyListener());
		this.setVisible(true);
		this.setFocusable(true);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, GAME_SCREEN_WIDTH, GAME_SCREEN_HEIGHT);
		if (!running && !gameover)
		{
			g.setColor(Color.white);
			g.setFont( new Font("Impact", Font.LAYOUT_LEFT_TO_RIGHT, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("press space to start", (GAME_SCREEN_WIDTH - metrics.stringWidth("press space to start"))/2, GAME_SCREEN_HEIGHT/2);
		}
		else if (running) 
		{
			// 80 pixel * 70 pixel
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), null);
			g.setColor(Color.red);
			
			for (Laser l : lasers)
			{
				g.fillRect(l.getX(), l.getY() - Laser.height, Laser.width, Laser.height);
			}
			for (Asteroid a : asteroids)
			{
				g.drawImage(a.getImage(), a.getX(), a.getY(), null);
			}
		}
		else if (!running && gameover)
		{
			// TODO score display
		}
		repaint();
	}
	
	public void startGame()
	{
		spaceship = new Spaceship();
		keys = new ArrayList<Integer>();
		asteroids = new ArrayList<Asteroid>();
		lasers = new ArrayList<Laser>();
		running = true;
		timer = new Timer(DELAY, new MyAtionListener());
		astroTimer = new Timer(astroDelay, new AstroActionListener());
		timer.start();
		astroTimer.start();
	}
	
	public void moveSpaceship()
	{
		for (int i : keys)
		{
			if (i == 87) // w
			{
				if (spaceship.getY() - spaceship.getSpeed() > 0)
				{
					spaceship.setY(spaceship.getY() - spaceship.getSpeed());
				}
			}
			if (i == 83) // s
			{
				if (spaceship.getY() + spaceship.getSpeed() < GAME_SCREEN_HEIGHT - spaceship.getImage().getHeight(null))
				{
					spaceship.setY(spaceship.getY() + spaceship.getSpeed());
				}				
			}
			if (i == 65) // a
			{
				if (spaceship.getX() - spaceship.getSpeed() > 0 - spaceship.getImage().getWidth(null) / 2)
				{
					spaceship.setX(spaceship.getX() - spaceship.getSpeed());
				}
			}
			if (i == 68) // d
			{
				if (spaceship.getX() + spaceship.getSpeed() < GAME_SCREEN_WIDTH - spaceship.getImage().getWidth(null) / 2)
				{
					spaceship.setX(spaceship.getX() + spaceship.getSpeed());
				}
			}	
		}
	}
	
	public void checkSpaceshipCollisions()
	{
		for (Asteroid a : asteroids)
		{
			// case 1, asteroid in the middle of the spaceship
			if ((a.getX() >= spaceship.getX()) && (a.getX() + a.getImage().getWidth(null) <= spaceship.getX() + spaceship.getImage().getWidth(null)) && (a.getY() + a.getImage().getHeight(null) >= spaceship.getY()) && (a.getY() <= spaceship.getY() + spaceship.getImage().getHeight(null)))
			{
				gameOver();
			}
			else if ((a.getX() >= spaceship.getX()) && (a.getX() <= spaceship.getX() + spaceship.getImage().getWidth(null)) && (a.getX() + a.getImage().getWidth(null) >= spaceship.getX() + spaceship.getImage().getWidth(null)) && (a.getY() + a.getImage().getHeight(null) >= spaceship.getY()) && (a.getY() <= spaceship.getY() + spaceship.getImage().getHeight(null)))
			{
				gameOver();
			}
			else if ((a.getX() + a.getImage().getWidth(null) >= spaceship.getX()) && (a.getX() + a.getImage().getWidth(null) <= spaceship.getX() + spaceship.getImage().getWidth(null)) && (a.getX() <= spaceship.getX()) && (a.getY() + a.getImage().getHeight(null) >= spaceship.getY()) && (a.getY() <= spaceship.getY() + spaceship.getImage().getHeight(null)))
			{
				gameOver();
			}
		}
		
	}
	
	
	public void checkLaserCollisions()
	{		
		Iterator<Asteroid> iterAste = asteroids.iterator();
		while (iterAste.hasNext()) 
		{
			Asteroid a = iterAste.next();
			Iterator<Laser> iterLaser = lasers.iterator();
			while (iterLaser.hasNext())
			{
				Laser l = iterLaser.next();
				if ((a.getX() <= l.getX() - Laser.width / 2) && (a.getX() + a.getImage().getWidth(null) >= l.getX()) && (a.getY() + a.getImage().getHeight(null) >= l.getY()) && (a.getY() <= l.getY() + Laser.height))
				{
					iterAste.remove();
					iterLaser.remove();
				}	
			}
		}
	}
	
	public void newAsteroid()
	{
		Asteroid asteroid = new Asteroid();
		asteroids.add(asteroid);
	}
	
	public void moveAsteroids()
	{
		for (Asteroid a : asteroids)
		{
			a.setY(a.getY() + Asteroid.speed);
		}
	}
	
	public void shootLasers()
	{
		Laser laser = new Laser();
		laser.setX(spaceship.getX() + spaceship.getImage().getWidth(null) / 2 - Laser.width / 2);
		laser.setY(spaceship.getY());
		lasers.add(laser);
	}
	
	public void moveLasers()
	{
		for (Laser l : lasers)
		{
			l.setY(l.getY() - Laser.speed);
		}
	}
	
	public void gameOver()
	{
		running = false;
		timer.stop();
		astroTimer.stop();
		gameover = true;
	}
	
	public class MyAtionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if (running)
			{
				moveLasers();
				moveSpaceship();
				moveAsteroids();
				checkLaserCollisions();
				checkSpaceshipCollisions();
			}
			repaint();
		}
	}
	
	public class MyKeyListener implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e) 
		{
			if (!running && e.getKeyChar() == ' ')
			{
				startGame();
			}
			else if (running && e.getKeyChar() == ' ')
			{
				shootLasers();
			}
		}

		@Override
		public void keyPressed(KeyEvent e) 
		{	
			if (running && !keys.contains(e.getKeyCode()))
			{
				keys.add(e.getKeyCode());
			}
		}

		@Override
		public void keyReleased(KeyEvent e) 
		{
			if(running && keys.contains(e.getKeyCode()))
			{
		        keys.remove(keys.indexOf(e.getKeyCode()));
		    }
		}	
	}
	
	public class AstroActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (running)
			{
				newAsteroid();
			}		
		}
		
	}

}
