
package game.gui;

import java.awt.Color;
import java.awt.Graphics2D;

import game.engine.Keyboard;
import game.engine.Mouse;

public class TextField extends GraphicalElement{

	Keyboard keyboard;
	Mouse mouse;
	int x, y;
	int width, height;
	String text;
	
	boolean focus = false;
	
	public TextField(Keyboard keyboard, Mouse mouse, String text, int x, int y, int width) {
		this.keyboard = keyboard;
		this.mouse = mouse;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = 25;
		this.text = text;
	}
	
	public void render(Graphics2D g2d) {
		
		g2d.setColor(Color.WHITE);
		g2d.drawRect(x, y, width, height);
		
		if(focus) {
			g2d.setColor(Color.GRAY);
		}else {
			g2d.setColor(Color.BLACK);
		}
		g2d.drawRect(x, y, width, height);
		
		g2d.setColor(Color.BLACK);
		g2d.setFont(font);
		g2d.drawString(text, x + 5, y + 18);
		
	}
	
	public void update() {
		//System.out.println(focus);
		/*
		if(mouse.isPressed) {
			if(
				mouse.mouseX > x &&
				mouse.mouseX < x + width &&
				mouse.mouseY > y &&
				mouse.mouseY < y + height
			) {
				if(!focus)
					keyboard.clearStream();
				focus = true;
			} else {
				focus = false;
			}
		}
		if(focus) {
			int n = 0;
			while((n = keyboard.getKeyStream()) != 0) {
				System.out.println(n);
				if(n >= 65 && n <= 90) {
					text += (char) n;
				}else if(n >= 48 && n <= 67) {
					text += (char) n;
				}else if(n == 44 || n == 46) {
					text += (char) n;
				}else if(n == KeyEvent.VK_BACK_SPACE) {
					if(text.length() > 0)
						text = text.substring(0, text.length() - 1);
				}
				
			}
			
		}
		*/
	}
	
	public String getText() {
		return text;
	}
	
}
