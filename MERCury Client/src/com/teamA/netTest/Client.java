package com.teamA.netTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.teamA.netTest.entity.Player;
import com.teamA.netTest.net.NetworkListener;
import com.teamA.netTest.net.packet.PacketDisconnect;
import com.teamA.netTest.net.packet.PacketFillRequest;
import com.teamA.netTest.net.packet.PacketLogin;
import com.teamA.netTest.net.packet.PacketMove;
import com.wessles.MERCury.framework.Core;
import com.wessles.MERCury.framework.Runner;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.in.Input;
import com.wessles.MERCury.math.MercMath;
import com.wessles.MERCury.net.MercClient;
import com.wessles.MERCury.res.ResourceManager;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 19, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class Client extends Core{
	
	Runner rnr = Runner.getInstance();
	Input in;
	
	MercClient client;
	Player player;
	Map<Integer, Player> players;
	
	private int udpP, tcpP;
	private String ip;

	public Client(int udpP, int tcpP, String ip) {
		super("MERCury Test Client");
		
		this.udpP = udpP;
		this.tcpP = tcpP;
		this.ip = ip;
		
		rnr.init(this, 800, 600);
		rnr.run();
	}

	@Override
	public void init(ResourceManager rm) {
		players = new HashMap<Integer, Player>();
		
		client = new MercClient(tcpP, udpP);
		client.createClient();
		client.connectToServer(ip);
		
		client.registerObject(PacketLogin.class);
		client.registerObject(PacketDisconnect.class);
		client.registerObject(PacketMove.class);
		client.registerObject(PacketFillRequest.class);
		
		client.addNetwork(new NetworkListener(client, players));
		
		System.out.println("Client started");
		
		player = new Player((int) MercMath.random(0, 800), (int) MercMath.random(0, 600));
		PacketLogin packet = new PacketLogin();
		packet.id = client.getID();
		packet.x = player.getX();
		packet.y = player.getY();
		client.sendTCP(packet);
	}

	@Override
	public void render(Graphics g) {
		for(Map.Entry<Integer, Player> entry : players.entrySet()){
			players.get(entry.getKey()).render(g);
		}
		player.render(g);
		g.drawString(1, 20, "Number of players connected: " + players.size());
	}

	@Override
	public void update(float delta) {
		in = rnr.input();
		player.update(client, in);
	}
	
	@Override
	public void cleanup(ResourceManager rm) {
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		System.out.print("Enter UDP port: ");
		int u = new Scanner(System.in).nextInt();
		System.out.print("Enter TCP port: "); 
		int t = new Scanner(System.in).nextInt();
		System.out.print("Enter the IP: ");
		String ip = new Scanner(System.in).nextLine();
		
		new Client(u, t, ip);
	}
}
