package com.teamA.netTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.teamA.netTest.net.NetworkListener;
import com.teamA.netTest.packets.PacketLogin;
import com.teamA.netTest.packets.PacketMove;
import com.wessles.MERCury.framework.Core;
import com.wessles.MERCury.framework.Runner;
import com.wessles.MERCury.graphics.Graphics;
import com.wessles.MERCury.in.Input;
import com.wessles.MERCury.net.MercClient;
import com.wessles.MERCury.res.ResourceManager;

/**
 * @from MERCury in com.teamA.netTest
 * @by opiop65
 * @website www.wessles.com
 * @license (C) Jan 18, 2014 www.wessles.com This file, and all others of the project 'MERCury' are licensed under WTFPL license. You can find the license itself at http://www.wtfpl.net/about/.
 */

public class TestClient extends Core{
	
	Runner rnr = Runner.getInstance();
	MercClient client;
	HashMap<Integer, Player> players;
	Player p;
	Random rand;
	
	private int udpPort, tcpPort;
	private String ip;
	
	public TestClient(int tcpPort, int udpPort, String ip) {
		super("MERCury Test Client");
		
		this.udpPort = udpPort;
		this.tcpPort = tcpPort;
		this.ip = ip;
		
		rnr.init(this, 800, 600);
		rnr.run();
	}

	@Override
	public void init(ResourceManager rm) {
		players = new HashMap<Integer, Player>();
		
		client = new MercClient(tcpPort, udpPort);
		client.createClient();
		client.registerObject(PacketMove.class);
		client.registerObject(PacketLogin.class);
		client.connectToServer(ip);
		client.addNetwork(new NetworkListener(players));
		
		rand = new Random();
		
		p = new Player(true, rand.nextInt(800), rand.nextInt(600));
	
		PacketLogin packet = new PacketLogin();
		packet.id = client.getID();
		packet.x = p.getX();
		packet.y = p.getY();
		
		client.sendTCP(packet);
	}
	
	@Override
	public void render(Graphics g) {
		for(Map.Entry<Integer, Player> entry : players.entrySet() ){
			players.get(entry.getKey()).render(g);
		}
		p.render(g);
	}

	@Override
	public void update(float delta) {
		Input in = rnr.input();
		p.update(client, in, delta);
	}
	
	@Override
	public void cleanup(ResourceManager rm) {
		client.close();
	}
	
	public static void main(String[] args) {
		/*System.out.print("Enter TCP port: ");
		@SuppressWarnings("resource")
		int tcpPort = new Scanner(System.in).nextInt();
		
		System.out.print("Enter UDP port: ");
		@SuppressWarnings("resource")
		int udpPort = new Scanner(System.in).nextInt();
		
		System.out.print("Enter IP address: ");
		@SuppressWarnings("resource")
		String ip = new Scanner(System.in).nextLine();*/
		
		new TestClient(8192, 8193, "localhost");
	}
}
