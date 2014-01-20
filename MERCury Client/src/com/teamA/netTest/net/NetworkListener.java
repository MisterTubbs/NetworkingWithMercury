package com.teamA.netTest.net;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.teamA.netTest.entity.Player;
import com.teamA.netTest.net.packet.PacketDisconnect;
import com.teamA.netTest.net.packet.PacketLogin;
import com.teamA.netTest.net.packet.PacketMove;
import com.wessles.MERCury.net.MercClient;
import com.wessles.MERCury.net.Network;

/**
 * @from MERCury in com.teamA.netTest.net
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 19, 2014 www.wessles.com This file, and all others of the
 *          project 'MERCury' are licensed under WTFPL license. You can find the
 *          license itself at http://www.wtfpl.net/about/.
 */

public class NetworkListener extends Network {

	// client
	MercClient client;
	Map<Integer, Player> players;

	public NetworkListener(MercClient client, Map<Integer, Player> players) {
		this.client = client;
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
		if (object instanceof PacketLogin) {
			players.put(((PacketLogin) object).id, new Player(((PacketLogin) object).x, ((PacketLogin) object).y));
		} else if (object instanceof PacketDisconnect) {
			players.remove(((PacketDisconnect) object).id);
		} else if (object instanceof PacketMove) {
			if (((PacketMove) object).id != client.getID()) {
				players.get(((PacketMove) object).id).move(((PacketMove) object).x, ((PacketMove) object).y);
			}
		}
	}
}
