package manequinn_src;

public class Communication {
	private Common.TYPECONNECT type;
	private SocketClient tcp;
	public Communication(Common.TYPECONNECT type_connect)
    {
        this.type = type_connect;
        //SelectType();
    }
    
    public Boolean Connected()
    {
        if (tcp != null)
            return tcp.Status_Connected();
        else return false;
    }
    public void ResetConnect()
    {
    	if (tcp != null)
            tcp.ResetConnectTcp();
        else
            return;
    }
    public Boolean Connect(Manequinn_gui view, String ip_addr, int port)
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		tcp = new SocketClient(view, ip_addr, port);
        		return tcp.Connect();
        			
        		//break;
        	case HDR_ETHERNET:
        		tcp = new SocketClient(view, ip_addr, port);
        		return tcp.Connect();
        		
            default:
            	return false;
        }
    }
    public void Close()
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		if (tcp != null)
        		{
        			tcp.Close();
        			tcp = null;
        			//this.Close();
        		}
        		break;
        	case HDR_ETHERNET:
        		if (tcp != null)
        		{
        			tcp.Close();
        			tcp = null;
        			//this.Close();
        		}
        		break;
            default:
            	break;
        }
    }
    
    public void Send_Command_No_Data(Common.COMMAND command_type)
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		if ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_No_Data(command_type);
        		}
        		break;
        	case HDR_ETHERNET:
        		if  ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_No_Data(command_type);
        		}
        		break;
            default:
            	break;
        }
    }
    
    public void Send_Command_One_Byte(Common.COMMAND command_type, byte byte_data)
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		if ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_One_Byte(command_type, byte_data);
        		}
        		break;
        	case HDR_ETHERNET:
        		if  ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_One_Byte(command_type, byte_data);
        		}
        		break;
            default:
            	break;
        }
    }
    
    public void Send_Command_Bytes(Common.COMMAND command_type, byte[] byte_data)
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		if ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_Bytes(command_type, byte_data);
        		}
        		break;
        	case HDR_ETHERNET:
        		if  ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_Bytes(command_type, byte_data);
        		}
        		break;
            default:
            	break;
        }
    }
    
    public void Send_Command_String(Common.COMMAND command_type, String user_data)
    {
    	switch (this.type)
        {
        	case HDR_WIFI:
        		if ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_String(command_type, user_data);
        		}
        		break;
        	case HDR_ETHERNET:
        		if  ((tcp != null) && (tcp.Status_Connected()))
        		{
        			tcp.Send_Command_String(command_type, user_data);
        		}
        		break;
            default:
            	break;
        }
    }
}
