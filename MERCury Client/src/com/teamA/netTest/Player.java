package com.teamA.netTest;

import org.lwjgl.input.Keyboard;

import com.teamA.netTest.packets.PacketMove;
import com.wessles.MERCury.geom.Rectangle;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.in.Input;
import com.wessles.MERCury.net.MercClient;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class Player extends Entity {

	static int moveSpeed = 7;
	boolean isPlayer;

	public Player(boolean isPlayer, int x, int y) {
		super(x, y);
		this.isPlayer = isPlayer;
	}

	public void render(Graphics g) {
		g.drawRect(new Rectangle(getX(), getY(), 50, 50));
	}

	public void update(MercClient client, Input in, float delta) {
		if (isPlayer) {
			if (in.keyDown(Keyboard.KEY_W)) {
				PacketMove p = new PacketMove();
				p.y = -moveSpeed;
				client.sendUDP(p);
				moveY(-moveSpeed);
			}

			if (in.keyDown(Keyboard.KEY_S)) {
				PacketMove p = new PacketMove();
				p.y = moveSpeed;
				client.sendUDP(p);
				moveY(moveSpeed);
			}

			if (in.keyDown(Keyboard.KEY_A)) {
				PacketMove p = new PacketMove();
				p.x = -moveSpeed;
				client.sendUDP(p);
				moveX(-moveSpeed);
			}

			if (in.keyDown(Keyboard.KEY_D)) {
				PacketMove p = new PacketMove();
				p.x = moveSpeed;
				client.sendUDP(p);
				moveX(moveSpeed);
			}
		}
	}
}
