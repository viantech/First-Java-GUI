package manequinn_src;

import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.swing.JOptionPane;


//import java.util.*;


public class SocketClient {
	private String ipAddr;
	private static Socket socket;
	//private static PrintWriter out;
	private OutputStream out;
	private Manequinn_gui pview;
	//private Thread rev_thread = null;
	private ReceivedStream runnableHandler = null;
	private int port;
	private volatile Boolean connect_ok = false;
	//private static BufferedReader in;

	public SocketClient() {

	}

	public SocketClient(Manequinn_gui view, String ipServer, int port) {
		this.ipAddr = ipServer;
		this.port = port;
		this.pview = view;
	}

	public boolean Connect() {
		// Create socket connection
		try {
			socket = new Socket();
			//socket.setSoTimeout(2000);
			socket.connect(new InetSocketAddress(this.ipAddr, this.port), 2000);
		} catch (IOException ec) {
			JOptionPane.showMessageDialog(null, "Socket Server not exist", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		//Create Stream I/O
		try {
			//out = new PrintWriter(socket.getOutputStream(), true);
			out = socket.getOutputStream();
			//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException ec) {
			JOptionPane.showMessageDialog(null, "I/O Stream Socket Error", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		connect_ok = true;
		//Thread recv from Server
		runnableHandler = new ReceivedStream(socket);
		//rev_thread = new Thread(runnableHandler);
		synchronized(runnableHandler){

			runnableHandler.start();
		}
		//rev_thread.start();
		//String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort() + "\nReady for transmitting data";
		//JOptionPane.showMessageDialog(null, msg, "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
		
		return true;

	}
	
	public void ResetConnectTcp()
	{
		connect_ok = false;
	}
	
	public Boolean Status_Connected()
	{
		return (connect_ok && socket.isConnected());
	}
	
	class ReceivedStream implements Runnable {
		private final Socket client;
		private volatile Thread blinker;
		ReceivedStream(Socket client){
			this.client = client;
		}
		
		public void start() {
	        blinker = new Thread(this);
	        blinker.start();
	    }
		
	    public void terminate() {
	    	blinker = null;
	    }
	    
		@Override
		public void run(){
			
			int red = -1;
			byte[] buffer = new byte[1024];
			byte[] redData;
			Thread thisThread = Thread.currentThread();
			
			while (blinker == thisThread){
				try{
					if ((red = client.getInputStream().read(buffer)) > 0){
						synchronized(this){
							notifyAll();
						}
						redData = new byte[red];
					    System.arraycopy(buffer, 0, redData, 0, red);
					    
					    if ((redData[red - 2] + (redData[red - 3] << 8)) == 1)
					    {
					    	//Common.SubFrameFormat sub_fmt = new Common.SubFrameFormat(HEADER.RESP_PACKET_HDR.getValue(), (short)(red - 1), (short)(redData[red - 2] + (redData[red - 3] << 8)));
					    	if  (Common.Decode_SubFrame(redData) != (byte)0)
					    	{
					    		byte[] meta_sub_frame = new byte[red - 6];
					    		System.arraycopy(redData, 3, meta_sub_frame, 0, red - 6);
					    		Data_Receive_Handler(meta_sub_frame);
					    		
					    	}
					    }
					}
					else{
						client.getInputStream().close();
						client.close();
						JOptionPane.showMessageDialog(null, "Server has close connection", "Socket I/O", JOptionPane.ERROR_MESSAGE);
						break;
					}
						
				}catch (IOException ioe){
					//Close();
					JOptionPane.showMessageDialog(null, "Close Input Stream", "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
			        break;
				}
			}
		}
	}
	
	private void Data_Receive_Handler(byte[] command_bytes){
		byte info_ack;
        //String data_response = "";
        byte[] byte_bits = null;
        //Common.COMMAND command_type = (Common.COMMAND)command_bytes[0];
        if (command_bytes != null && command_bytes.length > 0)
        {
        	if (command_bytes[0] == Common.COMMAND.CMD_CONNECTION_REQUEST.getValue())
        	{
        		byte_bits = Common.Decode_Frame(command_bytes[0], command_bytes);
        		if ((byte_bits != null) && (0x01 == byte_bits[0]))
        			{
        				
        				String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort() + "\nReady for transmitting data";
        				JOptionPane.showMessageDialog(null, msg, "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
        				pview.passMsg("Accepted", 1);
        			}
        		else
        			pview.passMsg("Not Request Connection", 1);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_DISCONNECT.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Disconnected.", 1);
        		else
        			pview.passMsg("Not Disconnect", 1);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_RESET_POSITION.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("OK", 1);
        		else
        			pview.passMsg("Not re-set position", 1);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_SET_POSITION.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Set OK", 1);
        		else
        			pview.passMsg("Not set position", 1);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_GET_POSITION.getValue())
        	{
        		byte_bits = null;
        		byte_bits = Common.Decode_Frame(command_bytes[0], command_bytes);
        		if (byte_bits != null)
        			{
        				if (byte_bits.length > 1)
        				{	
        					String recv_str = new String(Arrays.copyOfRange(byte_bits, 1, byte_bits.length - 1));
        					pview.passMsg(recv_str, 0);
        				}
        				else
        					//pview.passMsg("Not get position", 1); 
        					JOptionPane.showMessageDialog(null, "Wrong Command length", "Socket I/O", JOptionPane.ERROR_MESSAGE);
        			}
        		else
        			JOptionPane.showMessageDialog(null, "Wrong Command Frame", "Socket I/O", JOptionPane.ERROR_MESSAGE);
        	}
        }
	}
	
	public void Send_Command_No_Data(Common.COMMAND cmd)
	{
		Common.SubFrameFormat sub_frame = new Common.SubFrameFormat(Common.HEADER.PACKET_HDR.getValue(), Common.LENGTH.SUB_FRAME_NON_DATA.getValue(0), (short)1);
		Common.FrameFormat fmt_get = new Common.FrameFormat(cmd.getValue(), Common.LENGTH.FRAME_NON_DATA.getValue(0));
		sub_frame.Meta_Data(Common.Encode_Frame(fmt_get));
		SendBytes(Common.Encode_SubFrame(sub_frame));
	}
	
	public void Send_Command_One_Byte(Common.COMMAND cmd, byte data_byte)
	{
		Common.SubFrameFormat sub_frame = new Common.SubFrameFormat(Common.HEADER.PACKET_HDR.getValue(), Common.LENGTH.SUB_FRAME_NON_DATA.getValue(1), (short)1);
		Common.FrameFormat fmt_get = new Common.FrameFormat(cmd.getValue(), Common.LENGTH.FRAME_NON_DATA.getValue(1));
		fmt_get.metal_data = new byte[1];
		fmt_get.metal_data[0] = data_byte;
		sub_frame.Meta_Data(Common.Encode_Frame(fmt_get));
		SendBytes(Common.Encode_SubFrame(sub_frame));
	}
	
	public void Send_Command_Bytes(Common.COMMAND cmd, byte[] data_byte)
	{
		Common.SubFrameFormat sub_frame = new Common.SubFrameFormat(Common.HEADER.PACKET_HDR.getValue(), Common.LENGTH.SUB_FRAME_NON_DATA.getValue(data_byte.length), (short)1);
		Common.FrameFormat fmt_get = new Common.FrameFormat(cmd.getValue(), Common.LENGTH.FRAME_NON_DATA.getValue(data_byte.length));
		fmt_get.Meta_Data(data_byte);
		sub_frame.Meta_Data(Common.Encode_Frame(fmt_get));
		SendBytes(Common.Encode_SubFrame(sub_frame));
	}
	
	public void Send_Command_String (Common.COMMAND cmd, String user_data)
	{
		Common.SubFrameFormat sub_frame = new Common.SubFrameFormat(Common.HEADER.PACKET_HDR.getValue(), Common.LENGTH.SUB_FRAME_NON_DATA.getValue(user_data.length()), (short)1);
		Common.FrameFormat fmt_get = new Common.FrameFormat(cmd.getValue(), Common.LENGTH.FRAME_NON_DATA.getValue(user_data.length()));
		//byte[] data_byte = user_data.getBytes();
		fmt_get.Meta_Data(user_data.getBytes());
		sub_frame.Meta_Data(Common.Encode_Frame(fmt_get));
		SendBytes(Common.Encode_SubFrame(sub_frame));
	}
	
	private void SendBytes(byte[] data_stream)
	{
		if (this.Status_Connected())
			{
				try
				{
					out.write(data_stream);
					out.flush();
					synchronized(runnableHandler){
						runnableHandler.wait(50000);
					}
				}
				catch (IOException ioe){
					Close();
					JOptionPane.showMessageDialog(null, "Connection drop while transmit", "Socket I/O", JOptionPane.ERROR_MESSAGE);
				}
				catch (InterruptedException e){
					e.printStackTrace();
				}
			}
	}
	
	public void Close() {
		try {
			out.close();
			socket.close();
			ResetConnectTcp();
			runnableHandler.terminate();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Socket close not grateful", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}
}