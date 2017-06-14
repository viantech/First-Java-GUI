package manequinn_src;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFormattedTextField;
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
	//private List<Byte> join_command_bytes;
	private byte[] joined_bytes;
	//private static BufferedReader in;

	public SocketClient() {

	}

	public SocketClient(Manequinn_gui view, String ipServer, int port) {
		this.ipAddr = ipServer;
		this.port = port;
		this.pview = view;
		joined_bytes = new byte[0];
	}

	public boolean Connect() {
		// Create socket connection
		try {
			socket = new Socket();
			socket.setKeepAlive(true);
			socket.connect(new InetSocketAddress(this.ipAddr, this.port), 2000);
			out = socket.getOutputStream();
		} catch (IOException ec) {
			JOptionPane.showMessageDialog(null, "Socket Server not exist", "Socket I/O", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		//Create Stream I/O
		//out = new PrintWriter(socket.getOutputStream(), true);
			
		//in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
		private Socket client;
		private volatile Thread blinker;
		private InputStream inStream;
		ReceivedStream(Socket _client){
			try{
				this.client = _client;
				this.inStream = this.client.getInputStream();
			}
			catch (IOException ioe){
				JOptionPane.showMessageDialog(null, "Error Get Input Stream", "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
			}
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
			int length;
			int bytesRed = -1;
			byte[] buffer = new byte[1024];
			//byte[] redData;
			Thread thisThread = Thread.currentThread();
			
			while (blinker == thisThread){
				try{
					if (this.inStream.read(buffer, 0, 1) > 0){
						
						if (buffer[0] == Common.HEADER.RESP_PACKET_HDR.getValue()){
							if (this.inStream.read(buffer, 1, 2)> 0){
								length = (buffer[1] << 8) + buffer[2];
								int desired_len = length - 2;
                                int idx = 3;
                                while ((bytesRed = this.inStream.read(buffer, idx, desired_len)) > 0)
                                {
                                    desired_len = desired_len - bytesRed;
                                    idx += bytesRed;
                                    if (desired_len == 0)
                                        break;
                                }
                                length ++;
                                synchronized(this){
        							notifyAll();
        						}
                                //redData = new byte[length];
        					    //System.arraycopy(buffer, 0, redData, 0, length);
        					    
        					    if ((buffer[length - 2] + (buffer[length - 3] << 8)) == 1){
        					    	if  (Common.Check_SubFrame(buffer, length - 1) != (byte)0){
        					    		if (joined_bytes.length > 0){
        					    			joined_bytes = new byte[joined_bytes.length + length - 6];
        						    		System.arraycopy(buffer, 3, joined_bytes, joined_bytes.length - length + 6, length - 6);
        						    		Data_Receive_Handler(joined_bytes);
        						    		joined_bytes = new byte[0];
        					    		}
        					    		else{
        						    		byte[] meta_sub_frame = new byte[length - 6];
        						    		System.arraycopy(buffer, 3, meta_sub_frame, 0, length - 6);
        						    		Data_Receive_Handler(meta_sub_frame);
        					    		}
        					    	}
        					    }
        					    else{
        					    	if  (Common.Check_SubFrame(buffer, length - 1) != (byte)0){
        					    		joined_bytes = new byte[joined_bytes.length + length - 6];
        					    		System.arraycopy(buffer, 3, joined_bytes, joined_bytes.length - length + 6, length - 6);
        					    	}
        					    }
							}
						}
					}
					else{
						this.client.getOutputStream().close();
						this.inStream.close();
						this.client.close();
						ResetConnectTcp();
						blinker = null;
						JOptionPane.showMessageDialog(null, "Server has close connection", "Socket I/O", JOptionPane.ERROR_MESSAGE);
						pview.passMsg("Close", Common.COMMAND.CMD_DISCONNECT);
						break;
					}
						
				}catch (IOException ioe){
					ResetConnectTcp();
					blinker = null;
					JOptionPane.showMessageDialog(null, "Close Input Stream", "Socket I/O", JOptionPane.INFORMATION_MESSAGE);
					if (!socket.isClosed()){
							pview.passMsg("Close", Common.COMMAND.CMD_DISCONNECT);
					}
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
        				pview.passMsg("Accepted", Common.COMMAND.CMD_CONNECTION_REQUEST);
        			}
        		else
        			JOptionPane.showMessageDialog(null, "Failed Request Connection", "Socket I/O", JOptionPane.ERROR_MESSAGE);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_DISCONNECT.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Disconnected", Common.COMMAND.CMD_DISCONNECT);
        		else
        			pview.passMsg("Not Disconnect", Common.COMMAND.CMD_SET_POSITION);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_RESET_POSITION.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Reset done", Common.COMMAND.CMD_SET_POSITION);
        		else
        			pview.passMsg("Not re-set", Common.COMMAND.CMD_SET_POSITION);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_SET_POSITION.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Set done", Common.COMMAND.CMD_SET_POSITION);
        		else
        			pview.passMsg("Not set position", Common.COMMAND.CMD_SET_POSITION);
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
        					pview.passMsg(recv_str, Common.COMMAND.CMD_GET_POSITION);
        				}
        				else
        					//pview.passMsg("Not get position", 1); 
        					JOptionPane.showMessageDialog(null, "Wrong Command length", "Socket I/O", JOptionPane.ERROR_MESSAGE);
        			}
        		else
        			JOptionPane.showMessageDialog(null, "Wrong Command Frame", "Socket I/O", JOptionPane.ERROR_MESSAGE);
        	}
        	else if (command_bytes[0] == Common.COMMAND.CMD_REBOOT.getValue())
        	{
        		info_ack = Common.Decode_Frame_ACK(command_bytes[0], command_bytes);
        		if (0x01 == info_ack)
        			pview.passMsg("Reboot", Common.COMMAND.CMD_SET_POSITION);
        		else
        			pview.passMsg("Not Reboot", Common.COMMAND.CMD_SET_POSITION);
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