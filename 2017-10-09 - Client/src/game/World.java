package game;

import java.util.ArrayList;

import game.entity.Unit;

public class World {

	public final int MAP_WIDTH = 512;
	public final int MAP_HEIGHT = 512;
	public final int SKY = 0;
	public final int GROUND = 1;
	public byte[] map = new byte[MAP_WIDTH * MAP_HEIGHT];
	
	
	public ArrayList<Unit> unitList = new ArrayList<Unit>();
	
	
}
