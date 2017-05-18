package manequinn_src;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.util.*;


public class SocketClient {
	private String ipAddr;
	private static Socket socket;
	private static PrintWriter out;
	private Manequinn_gui pview;
	//private static BufferedReader in;

	public SocketClient() {

	}

	public SocketClient(Manequinn_gui view, String ipServer) {
		this.ipAddr = ipServer;
		this.pview = view;
	}

	public boolean Connect() {
		// Create socket connection
		try {
			socket = new Socket();
			//socket.setSoTimeout(2000);
			socket.connect(new InetSocketAddress(this.ipAddr, 4321), 2000);
		} catch (IOException ec) {
			JOptionPane.showMessageDialog(null, "Socket Server not exist", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		JOptionPane.showMessageDialog(null, msg, "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
		
		//Create Stream I/O
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ec) {
			JOptionPane.showMessageDialog(null, "I/O Stream Socket Error", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		//Thread recv from Server
		new Thread(new ReceivedStream(socket)).start();
		return true;

	}
	
	class ReceivedStream implements Runnable {
		private final Socket client;
		ReceivedStream(Socket client){
			this.client = client;
		}
		
		@Override
		public void run(){
			String line;
			int red = -1;
			byte[] buffer = new byte[5*1024];
			byte[] redData;
			while (client.isConnected()){
				try{
					if ((red = client.getInputStream().read(buffer)) > 0){
						redData = new byte[red];
					    System.arraycopy(buffer, 0, redData, 0, red);
					    line =  new String(redData,"UTF-8"); 
					    pview.passMsg(line);
						//System.out.println("Text received: " + line);
						//out.println("send "+ line);
					}
					else{
						client.getInputStream().close();
						client.close();
						System.out.println("Server has close connection");
						break;
					}
						
				}catch (IOException ioe){
					Close();
					System.out.println("Close connection");
			        break;
				}
			}
		}
	}
	
	public void Send(String msg){
		out.println(msg);
	}
	
	public void Close() {
		try {

			out.close();
			//in.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Socket close not grateful");
			System.exit(1);
		}

	}
}
