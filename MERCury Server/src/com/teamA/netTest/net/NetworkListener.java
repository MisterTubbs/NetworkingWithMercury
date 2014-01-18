package com.teamA.netTest.net;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.teamA.netTest.packets.PacketLogin;
import com.teamA.netTest.packets.PacketMove;
import com.wessles.MERCury.net.MercServer;
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

	MercServer server;

	List<Integer> clients = new ArrayList<Integer>();
	int id = 0;

	public NetworkListener(MercServer server) {
		this.server = server;
	}

	@Override
	public void connected(Connection connection) {
		clients.add(connection.getID());
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i) != connection.getID()) {
				PacketLogin p = new PacketLogin();
				p.id = clients.get(i);
				server.sendTCP(connection.getID(), p);
			}
		}
	}

	@Override
	public void disconnected(Connection connection) {
	}

	@Override
	public void received(Connection connection, Object object) {
		if(object instanceof PacketMove){
			System.out.println(((PacketMove) object).x + " , ");
			System.out.print(((PacketMove) object).y);
		}
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i) != connection.getID()) {
				server.sendTCP(clients.get(i), object);
			}
		}
	}
}
