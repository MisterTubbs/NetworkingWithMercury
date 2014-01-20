package com.teamA.netTest;

import java.util.HashMap;
import java.util.Map;

import com.teamA.netTest.net.NetworkListener;
import com.teamA.netTest.net.packet.PacketDisconnect;
import com.teamA.netTest.net.packet.PacketFillRequest;
import com.teamA.netTest.net.packet.PacketLogin;
import com.teamA.netTest.net.packet.PacketMove;
import com.wessles.MERCury.geom.Vector2f;
import com.wessles.MERCury.net.MercServer;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 19, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Server {

	MercServer server;
	Map<Integer, Vector2f> clients;
	
	private int uP, tP;
	
	public Server(int uP, int tP){
		this.uP = uP;
		this.tP = tP;
		init();
	}
	
	private void init(){
		clients = new HashMap<Integer, Vector2f>();
		
		server = new MercServer(tP, uP);
		server.createServer();
		
		server.registerObject(PacketLogin.class);
		server.registerObject(PacketDisconnect.class);
		server.registerObject(PacketMove.class);
		server.registerObject(PacketFillRequest.class);
		
		server.addNetwork(new NetworkListener(server, clients));
		System.out.println("Server started");
	}
	
	public static void main(String[] args){
		new Server(8193, 8192);
	}
}
