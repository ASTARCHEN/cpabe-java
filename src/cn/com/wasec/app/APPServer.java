package cn.com.wasec.app;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.jws.WebService;

import cn.edu.pku.ss.crypto.abe.apiV2.Server;

@WebService(endpointInterface = "cn.com.wasec.impl.IAPPServer", serviceName = "Service")
public class APPServer {
	public static APPServer lock = null;
	public static Server server = null;
	public static ServerSocket serverSocket = null;

	public APPServer(int port) {
		try {
			if (lock == null) {
				synchronized (APPServer.class) {
					if (lock == null) {
						server = new Server();
						serverSocket = new ServerSocket(port);
						lock = this;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class ServerThread extends Thread{
		private Socket socket = null;
		public ServerThread(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			
		}
	}
	
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				System.out.println("用户接入成功。。。");
				new ServerThread(socket).start();
				Thread.sleep(50);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
