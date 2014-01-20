package com.teamA.netTest.net;

import java.util.Map;

import com.esotericsoftware.kryonet.Connection;
import com.teamA.netTest.net.packet.PacketDisconnect;
import com.teamA.netTest.net.packet.PacketFillRequest;
import com.teamA.netTest.net.packet.PacketLogin;
import com.teamA.netTest.net.packet.PacketMove;
import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.net.MercServer;
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

	// server
	MercServer server;
	Map<Integer, Vector2f> clients;

	public NetworkListener(MercServer server, Map<Integer, Vector2f> clients) {
		this.server = server;
		this.clients = clients;
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
			PacketLogin packet = new PacketLogin();
			packet.id = ((PacketLogin) object).id;
			packet.x = ((PacketLogin) object).x;
			packet.y = ((PacketLogin) object).y;
			clients.put(packet.id, new Vector2f(packet.x, packet.y));
			server.sendTCPExcept(packet.id, packet);
		} else if (object instanceof PacketFillRequest) {
			for (Map.Entry<Integer, Vector2f> entry : clients.entrySet()) {
				if (((PacketFillRequest) object).id != entry.getKey()) {
					PacketLogin packet = new PacketLogin();
					packet.id = entry.getKey();
					packet.x = (int) clients.get(entry.getKey()).x;
					packet.y = (int) clients.get(entry.getKey()).y;
					server.sendTCP(((PacketFillRequest) object).id, packet);
				}
			}
		} else if (object instanceof PacketDisconnect) {
			PacketDisconnect packet = new PacketDisconnect();
			packet.id = ((PacketDisconnect) object).id;
			server.sendTCPExcept(packet.id, packet);
			clients.remove(((PacketDisconnect) object).id);
		} else if (object instanceof PacketMove) {
			PacketMove packet = new PacketMove();
			packet.id = ((PacketMove) object).id;
			packet.x = ((PacketMove) object).x;
			packet.y = ((PacketMove) object).y;
			server.sendTCPExcept(((PacketMove) object).id, packet);
		}
	}
}
