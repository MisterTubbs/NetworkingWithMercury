package com.teamA.netTest;

import com.wessles.MERCury.geom.Vector2f;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Entity {
	
	private Vector2f pos;
	
	public Entity(int x, int y){
		this(new Vector2f(x, y));
	}

	public Entity(Vector2f pos) {
		this.pos = pos;
	}
	
	public Vector2f getPos(){
		return pos;
	}
	
	public int getX(){
		return (int) pos.x;
	}
	
	public int getY(){
		return (int) pos.y;
	}
	
	public void moveX(int x){
		pos.x = getX() + x;
	}
	
	public void moveY(int y){
		pos.y = getY() + y;
	}
	
	public void move(int x, int y){
		moveX(x);
		moveY(y);
	}
}
