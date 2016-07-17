package ca.game.coul.gfx;
import java.awt.image.BufferedImage;

import ca.game.coul.gfx.ImageLoader;

public class Spritesheet {
	public BufferedImage sheet;
	
	public Spritesheet(String path) {
		this.sheet = ImageLoader.loadImage(path);
	}
	
	public BufferedImage getSprite(int x, int y, int width, int height) {
		return this.sheet.getSubimage(x, y, width, height);
	}
}
