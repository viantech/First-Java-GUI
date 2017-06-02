package manequinn_src;
import java.util.Arrays;

public class Common {

	public static class FrameFormat
	{
        public byte command;
        public short length;
        //[MarshalAs(UnmanagedType.ByValArray, SizeConst = 255)]
        public byte[] metal_data;
        public byte checksum;
        
        public FrameFormat(byte CMD, short len)
        {
        	this.command = CMD;
        	this.length = len;
        }
        
        public void Meta_Data (byte[] data)
        {
        	int meta_len = this.length - 4;
        	this.metal_data = new byte[meta_len];
            System.arraycopy(data, 0, this.metal_data, 0, meta_len);
        }
        
        public void Check_Sum(byte check)
        {
        	this.checksum = check;
        }
	}
	public static class SubFrameFormat
	{
        public byte header;
        public short length;
        //[MarshalAs(UnmanagedType.ByValArray, SizeConst = 255)]
        public byte[] metal_data;
        public short truncate;
        public byte checksum;
        public SubFrameFormat(byte head, short len, short num_packet)
        {
        	this.header = head;
        	this.length = len;
        	this.truncate = num_packet;
        }
        public void Meta_Data (byte[] data)
        {
        	int meta_len = this.length - 5;
        	this.metal_data = new byte[meta_len];
            System.arraycopy(data, 0, this.metal_data, 0, meta_len);
        }
        public void Check_Sum(byte check)
        {
        	this.checksum = check;
        }
	}
	//private static int Value;
    public enum HEADER
    {
        PACKET_HDR(0xE5),
        RESP_PACKET_HDR(0xE4);
    	private int Value;
    	HEADER(int numVal)
    	{
    		this.Value = numVal;
    	}
    	public byte getValue()
    	{
    		return (byte)Value;
    	}

    };

    public enum LENGTH 
    {
        FRAME_NON_DATA(0x04),
        SUB_FRAME_NON_DATA(0x09),
        SIZE_SUB_FRAME_APPEND(0x06),
        MAX_SIZE_TCP_META_DATA(0x03FA), // 1024 - 6
        MAX_SIZE_ZIGBEE_META_DATA(0x42), //72 -6
        MAX_SIZE_ZIGBEE_BLOCK(0x66), //102
        CHUNK_SIZE_FILE(0xC350); //65500
    	private int Value;
        LENGTH(int numVal)
    	{
    		this.Value = numVal;
    	}
        
    	public short getValue(int real_data)
    	{
    		return (short)(Value + real_data);
    	}
    };

    public enum TYPECONNECT
    {
        HDR_WIFI,
        HDR_ETHERNET,
        HDR_RS232,
        HDR_BLUETOOTH,
        HDR_ZIGBEE
    }
    
    public enum COMMAND
    {
    	CMD_CONNECTION_REQUEST(0x01),
    	CMD_DISCONNECT(0x02),
    	CMD_SET_POSITION(0x03),
    	CMD_GET_POSITION(0x04),
    	CMD_RESET_POSITION(0x05),
    	CMD_SET_CONN_TYPE(0x06),
    	CMD_SET_CONN_PROP(0x07),
    	CMD_GET_CONN_PROP(0x08),
    	CMD_GET_STATUS(0x09),
    	CMD_REBOOT(0x10),
    	;
    	private int Value;
    	COMMAND(int cmd_byte)
    	{
    		this.Value = cmd_byte;
    	}
    	
    	public byte getValue()
    	{
    		return (byte)Value;
    	}
    }
    /*
     * Method to encoding Frame Format
     */
    
    
    /*
     * Calculate Checksum
     */
    public static byte Chcksum(byte[] data, int length)
    {
        byte sum = 0;
        for (int i = 0; i < length; i++)
            sum += data[i];
        sum = (byte)((byte)~sum + 1);

        return sum;
    }
    
    
    /*
     * Covert Frame Format to byte array
     */
    public static byte[] Encode_Frame(FrameFormat fmt)
    {
    	//fmt = new FrameFormat();
        byte[] byte_frame = new byte[fmt.length];
        byte_frame[0] = fmt.command;
        byte_frame[1] = (byte)((fmt.length >> 8) & 0xff);
        byte_frame[2] = (byte)(fmt.length & 0xff);
        if (fmt.length > 4)
        	System.arraycopy(fmt.metal_data, 0, byte_frame, 3, fmt.length - 4);
        byte_frame[fmt.length - 1] = Chcksum(byte_frame, fmt.length - 1);
        return byte_frame;
    }
    
    /*
     * Covert Frame Format to byte array
     */
    public static byte[] Encode_SubFrame(SubFrameFormat sub_fmt)
    {
    	//fmt = new FrameFormat();
        byte[] byte_frame = new byte[sub_fmt.length + 1];
        byte_frame[0] = sub_fmt.header;
        byte_frame[1] = (byte)((sub_fmt.length >> 8) & 0xff);
        byte_frame[2] = (byte)(sub_fmt.length & 0xff);
        if (sub_fmt.length > 5)
        	System.arraycopy(sub_fmt.metal_data, 0, byte_frame, 3, sub_fmt.length - 5);
        byte_frame[sub_fmt.length - 2] = (byte)((sub_fmt.truncate >> 8) & 0xff);
        byte_frame[sub_fmt.length - 1] = (byte)(sub_fmt.truncate & 0xff);
        byte_frame[sub_fmt.length] = Chcksum(Arrays.copyOfRange(byte_frame, 1, sub_fmt.length), sub_fmt.length - 1);
        return byte_frame;
    }
    
    /*
     * Check validate read byte data
     */
    public static byte Decode_SubFrame(byte[] byte_data)
    {
    	//SubFrameFormat sub_fmt = new SubFrameFormat(HEADER.RESP_PACKET_HDR.getValue(), (short)(byte_data.length - 1), (short)1);
        //int datalen_fmt;
        if (byte_data == null || byte_data.length == 0)
            return 0;
        /* Frame Format*/
        //Command
        if (HEADER.RESP_PACKET_HDR.getValue() != byte_data[0])
            return 0;

        //Length
        int sub_len = byte_data.length - 1;
        //fmt.length = (short)(byte_data[2] + (byte_data[1] << 8));
        if (sub_len != (short)(byte_data[2] + (byte_data[1] << 8)))
            return 0;
        
        //CRC
        //sub_fmt.Check_Sum(byte_data[sub_fmt.length]);
        if (byte_data[sub_len] != Chcksum(Arrays.copyOfRange(byte_data, 1, sub_len), sub_len - 1))
            return 0;

        /* Data Frame Format*/
        //sub_fmt.Meta_Data(byte_data);
        return 1;
    }
    
    /*
     * Convert byte array to valid Frame Format
     */
    public static byte[] Decode_Frame(byte command_option, byte[] byte_data)
    {
        FrameFormat fmt = new FrameFormat(command_option, (short)byte_data.length);
        //int datalen_fmt;
        if (byte_data == null || byte_data.length == 0)
            return null;
        /* Frame Format*/
        //Command
        //fmt.command = meta_subframe[0];
        //if (fmt.command != byte_data[0])
            //return new byte[] { 0x01, 0x01 };

        //Length
        //fmt.length = (short)(byte_data[2] + (byte_data[1] << 8));
        if (fmt.length != (short)(byte_data[2] + (byte_data[1] << 8)))
        	return null;

        //CRC
        fmt.Check_Sum(byte_data[fmt.length - 1]);
        if (fmt.checksum != Chcksum(byte_data, fmt.length - 1))
        	return null;

        /* Data Frame Format*/
        fmt.metal_data = new byte[fmt.length - 4];
        System.arraycopy(byte_data, 3, fmt.metal_data, 0, fmt.length - 4);
        return fmt.metal_data;
    }

    public static byte Decode_Frame_ACK(byte command_option, byte[] byte_data)
    {
    	FrameFormat fmt = new FrameFormat(command_option, (short)byte_data.length);
        //int datalen_fmt;
        if (byte_data == null || byte_data.length == 0)
            return 0;
        /* Frame Format*/
        //Command
        if (fmt.command != byte_data[0])
            return 0;

        //Length
        if (fmt.length != (short)(byte_data[2] + (byte_data[1] << 8)))
        //if (fmt.length != 5)
            return 0;

        //CRC
        fmt.Check_Sum(byte_data[fmt.length - 1]);
        if (fmt.checksum != Chcksum(byte_data, fmt.length - 1))
            return 0;

        return byte_data[3];
    }
    
    public static String Get_Data(byte[] byte_data)
    {
        return new String(Arrays.copyOfRange(byte_data, 1, byte_data.length - 1));
    }
}
