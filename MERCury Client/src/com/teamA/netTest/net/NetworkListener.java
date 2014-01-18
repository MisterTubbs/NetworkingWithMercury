package com.teamA.netTest.net;

import java.util.HashMap;

import com.esotericsoftware.kryonet.Connection;
import com.teamA.netTest.Player;
import com.teamA.netTest.packets.PacketLogin;
import com.teamA.netTest.packets.PacketMove;
import com.wessles.MERCury.net.Network;

/**
 * @from MERCury in com.teamA.netTest.net
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class NetworkListener extends Network {

	private HashMap<Integer, Player> players;

	public NetworkListener(HashMap<Integer, Player> players) {
		this.players = players;
	}

	@Override
	public void connected(Connection connection) {
	}

	@Override
	public void disconnected(Connection connection) {
	}

	@Override
	public void received(Connection connection, Object object) {
		if (object instanceof PacketMove) {
			System.out.println(((PacketMove) object).id);
			Player player = players.get(((PacketMove) object).id);
			if (player != null) {
				player.move(((PacketMove) object).x, ((PacketMove) object).y);
				System.out.println("Player has moved");
			}
		} else if (object instanceof PacketLogin) {
			players.put(((PacketLogin) object).id, new Player(true,
					((PacketLogin) object).x, ((PacketLogin) object).y));
			System.out.println("Player has joined the game");
		}
	}
}
