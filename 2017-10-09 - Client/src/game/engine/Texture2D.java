package game.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import game.Main;

public class Texture2D {

	public int id;

	public Texture2D(String path) {
		//System.out.println("Texture2D constructor: path: " + path);
		BufferedImage image = loadImage(path);
		id = loadTexture(image);
	}

	public Texture2D(BufferedImage image) {
		id = loadTexture(image);
	}

	public static int loadTexture(BufferedImage image) {
		
		//System.out.println(image.getWidth() + " " + image.getType());
		
		/*
		System.out.println("TYPE_3BYTE_BGR " + BufferedImage.TYPE_3BYTE_BGR);
		System.out.println("TYPE_4BYTE_ABGR " + BufferedImage.TYPE_4BYTE_ABGR);
		System.out.println("TYPE_INT_ARGB " + BufferedImage.TYPE_INT_ARGB);
		System.out.println("TYPE_INT_RGB " + BufferedImage.TYPE_INT_RGB);
		*/
		
		/*
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();
		*/
		
		
		/*
		byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

		for (int i = 0; i < pixels.length / 4; i++) {
			buffer.put(pixels[i * 4 + 3]);
			buffer.put(pixels[i * 4 + 2]);
			buffer.put(pixels[i * 4 + 1]);
			buffer.put(pixels[i * 4 + 0]);
		}

		buffer.flip();
		*/
		
		
		byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		int[] ipixels = null;
		int internalFormat = 0;
		
		switch(image.getType()) {
		case BufferedImage.TYPE_3BYTE_BGR:
			ipixels = new int[pixels.length / 3];
			for (int i = 0; i < ipixels.length; i++) {
				int n = ((pixels[i * 3 + 2] & 0xFF) << 0) |
						((pixels[i * 3 + 1] & 0xFF) << 8) |
						((pixels[i * 3 + 0] & 0xFF) << 16);
				ipixels[i] = n;
			}
			internalFormat = GL_RGBA8;
			break;
		case BufferedImage.TYPE_4BYTE_ABGR:
			ipixels = new int[pixels.length / 4];
			for (int i = 0; i < ipixels.length; i++) {
				int n = ((pixels[i * 4 + 3] & 0xFF) << 0) |
						((pixels[i * 4 + 2] & 0xFF) << 8) |
						((pixels[i * 4 + 1] & 0xFF) << 16) |
						((pixels[i * 4 + 0] & 0xFF) << 24);
				ipixels[i] = n;
			}
			internalFormat = GL_RGB8;
			break;
			default:
				System.out.println("Texture2D: Bufferedimage unrecognized type: " + image.getType());
				break;
		}
		
		
	
		
		/*
		
		byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();
		
		ByteBuffer buffer = ByteBuffer.wrap(pixels);
		
		System.out.println("position: " + buffer.position());
		System.out.println("limit: " + buffer.limit());
		
		//printBytes(buffer.array());
		*/
		
		
		
		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

		glTexImage2D(GL_TEXTURE_2D, 0, internalFormat, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, ipixels);

		return id;
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Main.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

}
