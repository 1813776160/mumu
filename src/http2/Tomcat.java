package http2;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Tomcat {
	
	public static void main(String[] args) throws IOException {
		Tomcat tomcat=new Tomcat();
		tomcat.startup();
	}

	public void startup() throws IOException {
		//套接字服务器
		ServerSocket server=new ServerSocket(8080);
		while(true){
			Socket socket =server.accept();
			new Thread(){
				public void run(){
					new Processer().process(socket);;
				}
			}.start();
		}
	}
}
