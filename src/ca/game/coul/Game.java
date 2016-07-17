package ca.game.coul;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 200;
	public static final int HEIGHT = 150;
	public static final int SCALE = 3;
	public static final String NAME = "Simon Says";
	
	private JFrame frame;
	
	public boolean running = false;
	public int tickCount = 0;
	
	public Game() {
		setMinimumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setMaximumSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		frame = new JFrame(NAME);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this,BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private synchronized void start() {
		running = true;
		init();
		new Thread(this).start();
		
	}
	
	private synchronized void stop() {
		running = false;
	}
	
	public void init() {
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		
		int frames = 0;
		int ticks = 0;
		
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = false;
			
			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (shouldRender) {
				frames++;
				render();
			}
			
			frames++;
			render();
			
			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " , " + frames);
				ticks = 0;
				frames = 0;
			}
		}
	}
	
	public void tick() {
		tickCount++;
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 600, 600);
		
		bs.show();
		g.dispose();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

}

