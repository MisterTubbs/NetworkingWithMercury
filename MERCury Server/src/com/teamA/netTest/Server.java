package com.teamA.netTest;

import com.teamA.netTest.net.NetworkListener;
import com.teamA.netTest.packets.PacketLogin;
import com.teamA.netTest.packets.PacketMove;
import com.wessles.MERCury.net.MercServer;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Server {
	
	MercServer server;
	int udpPort, tcpPort;
	
	public Server(int udpPort, int tcpPort){

		this.udpPort = udpPort;
		this.tcpPort = tcpPort;
		
		init();
	}

	public void init() {
		System.out.println("Server started");
		server = new MercServer(tcpPort, udpPort);
		server.createServer();
		server.registerObject(PacketMove.class);
		server.registerObject(PacketLogin.class);
		server.addNetwork(new NetworkListener(server));
	}

	public void update() {
	}
	
	public void cleanup() {
	}
	
	public static void main(String[] args) {
		/*System.out.print("Enter TCP port: ");
		@SuppressWarnings("resource")
		int tcpPort = new Scanner(System.in).nextInt();
		
		System.out.print("Enter UDP port: ");
		@SuppressWarnings("resource")
		int udpPort = new Scanner(System.in).nextInt();*/
		
		new Server(8193, 8192);
	}
}
