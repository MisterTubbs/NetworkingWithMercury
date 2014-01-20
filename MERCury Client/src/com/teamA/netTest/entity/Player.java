package com.teamA.netTest.entity;

import org.lwjgl.input.Keyboard;

import com.teamA.netTest.net.packet.PacketMove;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.in.Input;
import com.wessles.MERCury.net.MercClient;

/**
 * @from MERCury in com.teamA.netTest.entity
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 19, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Player {
	
	private Vector2f pos;
	
	private int moveSpeed = 7;
	
	public Player(int x, int y){
		this(new Vector2f(x, y));
	}

	public Player(Vector2f pos) {
		this.pos = pos;
	}
	
	public void update(MercClient client, Input in){
		if(in.keyDown(Keyboard.KEY_W)){
			PacketMove p = new PacketMove();
			p.y = -moveSpeed;
			client.sendTCP(p);
			moveY(-moveSpeed);
		}
		if(in.keyDown(Keyboard.KEY_S)){
			PacketMove p = new PacketMove();
			p.y = moveSpeed;
			client.sendTCP(p);
			moveY(moveSpeed);
		}
		if(in.keyDown(Keyboard.KEY_A)){
			PacketMove p = new PacketMove();
			p.x = -moveSpeed;
			client.sendTCP(p);
			moveX(-moveSpeed);
		}
		if(in.keyDown(Keyboard.KEY_D)){
			PacketMove p = new PacketMove();
			p.x = moveSpeed;
			client.sendTCP(p);
			moveX(moveSpeed);
		}
	}

	public void render(Graphics g){
		g.drawRect(new Rectangle(getX(), getY(), 50, 50));
	}
	
	public int getX(){
		return (int) pos.x;
	}
	
	public int getY(){
		return (int) pos.y;
	}
	
	public Vector2f getPos(){
		return pos;
	}
	
	public void moveX(int x){
		this.pos.x = getX() + x;
	}
	
	public void moveY(int y){
		this.pos.y = getY() + y;
	}
	
	public void move(int x, int y){
		moveX(x);
		moveY(y);
	}
}
