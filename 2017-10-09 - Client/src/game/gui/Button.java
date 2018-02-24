
package game.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import game.engine.Keyboard;
import game.engine.Mouse;

public class Button extends GraphicalElement {

	Keyboard keyboard;
	Mouse mouse;
	String text;
	int x, y;
	int width, height;

	boolean pressed = false;
	boolean clicked = false;

	public Button(Keyboard keyboard, Mouse mouse, String text, int x, int y, int width, int height) {
		this.keyboard = keyboard;
		this.mouse = mouse;
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void render(Graphics2D g2d) {

		g2d.setColor(Color.WHITE);
		g2d.fillRect(x, y, width, height);

		if (pressed)
			g2d.setColor(Color.GRAY);
		else
			g2d.setColor(Color.BLACK);
		g2d.drawRect(x, y, width, height);

		g2d.setColor(Color.BLACK);
		g2d.setFont(font);

		FontMetrics metrics = g2d.getFontMetrics(font);
		int w = metrics.stringWidth(text);
		int h = metrics.getHeight();
		g2d.drawString(text, x + width / 2 - w / 2, y + height / 2 - h / 2 + 18);

	}

	public void update() {
		/*
		if(mouse.isPressed &&
			mouse.mouseX > x &&
			mouse.mouseX < x + width &&
			mouse.mouseY > y &&
			mouse.mouseY < y + height
		) {
			pressed = true;
		}else {
			if(pressed)
				clicked = true;
			pressed = false;
		}
		*/
	}

	public boolean wasClicked() {
		if (clicked) {
			clicked = false;
			return true;
		}
		return false;
	}
}
