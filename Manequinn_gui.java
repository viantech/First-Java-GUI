package manequinn_src;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.Image;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
//import java.awt.Component;

interface StringReceiver {
	void passMsg(String msg, int set_type);
}

public class Manequinn_gui implements StringReceiver {

	private static Manequinn_gui window;
	private JButton btnSetSize;
	private JButton btnGetSize ;
	private JFormattedTextField txNeck;
	private JFormattedTextField txChest;
	private JFormattedTextField txBoobs;
	private JFormattedTextField txBelly;
	private JFormattedTextField txAss;
	private JFormattedTextField txHeigh;
	private JPanel BodyGroup;
	private JFormattedTextField txPort;
	private JLabel txReceive;
	private JFrame frame;
	private JTextField txAddress;
	//SocketClient tcpClient;
	private Communication com;
	private List<JFormattedTextField> list_body_parts;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new Manequinn_gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Manequinn_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		list_body_parts = new ArrayList<JFormattedTextField>();
		Image img_ico = new ImageIcon(this.getClass().getResource("/Icon_SD.png")).getImage();
		frame = new JFrame();
		frame.setBounds(100, 100, 910, 605);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(img_ico);
		frame.setTitle("Seldat Robot Manequinn");
		BodyGroup = new JPanel();
		BodyGroup.setBounds(25, 109, 290, 396);
		BodyGroup.setBorder(BorderFactory.createTitledBorder("Body Measurement"));
		frame.getContentPane().add(BodyGroup);
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);
	    NumberFormatter formatter = new NumberFormatter(format);
	    
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(0);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(true);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
		Image img_add = new ImageIcon(this.getClass().getResource("/add.gif")).getImage();
		Image img_sub = new ImageIcon(this.getClass().getResource("/remove_correction.png")).getImage();
		
		JLabel lblNeck = new JLabel("Neck");
		lblNeck.setLabelFor(txNeck);
		txNeck = new JFormattedTextField(formatter);
		txNeck.setText("0");
		txNeck.setHorizontalAlignment(SwingConstants.CENTER);
		txNeck.setColumns(10);
		
		JLabel de_neck = new JLabel("");
		de_neck.setIcon(new ImageIcon(img_sub));
		de_neck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Sub_Text(txNeck);
			}
		});
		
		
		JLabel add_neck = new JLabel("");
		add_neck.setIcon(new ImageIcon(img_add));
		add_neck.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txNeck);
			}
		});

		JLabel lblChest = new JLabel("Chest");
		txChest = new JFormattedTextField(formatter);
		txChest.setHorizontalAlignment(SwingConstants.CENTER);
		txChest.setText("0");
		txChest.setColumns(10);
		lblChest.setLabelFor(txChest);
		
		
		JLabel de_chest = new JLabel("");
		de_chest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txChest);
			}
		});
		de_chest.setIcon(new ImageIcon(img_sub));
		
		JLabel add_chest = new JLabel("");
		add_chest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Incr_Text(txChest);
			}
		});
		add_chest.setIcon(new ImageIcon(img_add));
		
		JLabel lbBust = new JLabel("Bust");
		lbBust.setLabelFor(txBoobs);
		txBoobs = new JFormattedTextField(formatter);
		txBoobs.setHorizontalAlignment(SwingConstants.CENTER);
		txBoobs.setText("0");
		txBoobs.setColumns(10);
		
		JLabel de_bust = new JLabel("");
		de_bust.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txBoobs);
			}
		});
		de_bust.setIcon(new ImageIcon(img_sub));
		
		JLabel add_bust = new JLabel("");
		add_bust.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Incr_Text(txBoobs);
			}
		});
		add_bust.setIcon(new ImageIcon(img_add));
		
		JLabel lbWaist = new JLabel("Waist");
		lbWaist.setLabelFor(txBelly);
		txBelly = new JFormattedTextField(formatter);
		txBelly.setHorizontalAlignment(SwingConstants.CENTER);
		txBelly.setText("0");
		txBelly.setColumns(10);
		
		JLabel de_waist = new JLabel("");
		de_waist.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
				Sub_Text(txBelly);
			}
		});
		de_waist.setIcon(new ImageIcon(img_sub));
		
		JLabel add_waist = new JLabel("");
		add_waist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txBelly);
			}
		});
		add_waist.setIcon(new ImageIcon(img_add));
		
		JLabel lblHip = new JLabel("Hips");
		lblHip.setLabelFor(txAss);
		txAss = new JFormattedTextField(formatter);
		txAss.setHorizontalAlignment(SwingConstants.CENTER);
		txAss.setText("0");
		txAss.setColumns(10);
		
		JLabel de_hip = new JLabel("");
		de_hip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txAss);
			}
		});
		de_hip.setIcon(new ImageIcon(img_sub));
		
		JLabel add_hip = new JLabel("");
		add_hip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txAss);
			}
		});
		add_hip.setIcon(new ImageIcon(img_add));
		
		JLabel lblHeight = new JLabel("Heigh");
		lblHeight.setLabelFor(txHeigh);
		txHeigh = new JFormattedTextField(formatter);
		txHeigh.setHorizontalAlignment(SwingConstants.CENTER);
		txHeigh.setText("0");
		txHeigh.setColumns(10);
		
		JLabel de_heigh = new JLabel("");
		de_heigh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txHeigh);
			}
		});
		de_heigh.setIcon(new ImageIcon(img_sub));
		
				JLabel add_heigh = new JLabel("");
				add_heigh.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Incr_Text(txHeigh);
					}
				});
				add_heigh.setIcon(new ImageIcon(img_add));
		
		btnSetSize = new JButton("Set");
		btnSetSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (com != null)
				{
					String body_size = "[" + txNeck.getText() + "]"
								 	+ "[" + txChest.getText() + "]"
								 	+ "[" + txBoobs.getText() + "]"
								 	+ "[" + txBelly.getText() + "]"
								 	+ "[" + txAss.getText() + "]"
								 	+ "[" + txHeigh.getText() + "]";
					com.Send_Command_String(Common.COMMAND.CMD_SET_POSITION, body_size);	
				}	
			}
		});
		
		btnGetSize = new JButton("Get");
		btnGetSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (com != null)
					com.Send_Command_No_Data(Common.COMMAND.CMD_GET_POSITION);
			}
		});
		
		JLabel lblMm = new JLabel("Unit: mm");
		lblMm.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		GroupLayout gl_BodyGroup = new GroupLayout(BodyGroup);
		gl_BodyGroup.setHorizontalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblHip, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(210))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lbWaist, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(210))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lbBust, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(210))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblChest, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(210))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblNeck)
							.addGap(233))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblMm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(txNeck, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
								.addComponent(txChest, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
								.addComponent(txBoobs, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
								.addComponent(txBelly, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
								.addComponent(txAss, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
								.addComponent(txHeigh, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_BodyGroup.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
										.addComponent(de_neck)
										.addComponent(de_chest)
										.addComponent(de_bust)
										.addComponent(de_waist)
										.addComponent(de_hip)
										.addComponent(de_heigh))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_BodyGroup.createSequentialGroup()
											.addComponent(add_heigh)
											.addPreferredGap(ComponentPlacement.RELATED, 72, Short.MAX_VALUE)
											.addComponent(btnSetSize, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
										.addComponent(add_neck)
										.addComponent(add_chest)
										.addComponent(add_bust)
										.addComponent(add_waist)
										.addComponent(add_hip)))
								.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
									.addGap(124)
									.addComponent(btnGetSize, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap())))
		);
		gl_BodyGroup.setVerticalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_BodyGroup.createSequentialGroup()
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addComponent(add_neck)
						.addComponent(de_neck)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblNeck)
							.addGap(3)
							.addComponent(txNeck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblChest)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
								.addComponent(add_chest)
								.addComponent(txChest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(de_chest))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lbBust)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
								.addComponent(add_bust)
								.addComponent(txBoobs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(de_bust))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lbWaist)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txBelly, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(de_waist)
						.addComponent(add_waist))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblHip)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txAss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(de_hip)
						.addComponent(add_hip))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblHeight)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(txHeigh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(de_heigh)
						.addComponent(add_heigh))
					.addGap(18)
					.addComponent(lblMm)
					.addContainerGap(14, Short.MAX_VALUE))
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addContainerGap(316, Short.MAX_VALUE)
					.addComponent(btnSetSize)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnGetSize)
					.addContainerGap())
		);
		BodyGroup.setLayout(gl_BodyGroup);
		
		JPanel ConnectionGroup = new JPanel();
		ConnectionGroup.setBounds(25, 11, 290, 87);
		frame.getContentPane().add(ConnectionGroup);
		ConnectionGroup.setBorder(BorderFactory.createTitledBorder("Connection Properties"));
		
		JLabel lblIp = new JLabel("IP:");
		
		txAddress = new JTextField();
		txAddress.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (!validateIP(txAddress.getText()))
				{
					infoBox("Please input the valid IP Address format", "Invalid IP Address");
					txAddress.requestFocus();
				}
			}
		});
		lblIp.setLabelFor(txAddress);
		txAddress.setText("192.168.1.4");
		txAddress.setColumns(10);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//tcpClient = new SocketClient (window, txAddress.getText(), 4321);
				if (btnConnect.getText() == "Connect")
				{
					com = new Communication(Common.TYPECONNECT.HDR_WIFI);
					if (com.Connect(window, txAddress.getText(), Integer.parseInt(txPort.getText())))
						{
							com.Send_Command_No_Data(Common.COMMAND.CMD_CONNECTION_REQUEST);
							btnConnect.setText("Disconnect");
						}
					else
						infoBox("Cannot connect", "Socket Error");
				}
				else
				{
					com.Send_Command_No_Data(Common.COMMAND.CMD_DISCONNECT);
					com.Close();
					com = null;
					btnConnect.setText("Connect");
				}
			}
		});
		
		JLabel lblPort = new JLabel("Port:");
		
		txPort = new JFormattedTextField(formatter);
		lblPort.setLabelFor(txPort);
		txPort.setText("5000");
		txPort.setColumns(10);
		GroupLayout gl_ConnectionGroup = new GroupLayout(ConnectionGroup);
		gl_ConnectionGroup.setHorizontalGroup(
			gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIp)
						.addComponent(lblPort))
					.addGap(18)
					.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(txAddress, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(txPort, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
					.addComponent(btnConnect)
					.addContainerGap())
		);
		gl_ConnectionGroup.setVerticalGroup(
			gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_ConnectionGroup.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIp)
								.addComponent(txAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPort)
								.addComponent(txPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_ConnectionGroup.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		ConnectionGroup.setLayout(gl_ConnectionGroup);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 541, 46, 14);
		frame.getContentPane().add(lblStatus);
		
		txReceive = new JLabel("Idle");
		txReceive.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setLabelFor(txReceive);
		txReceive.setBounds(94, 541, 221, 14);
		frame.getContentPane().add(txReceive);

		Image img1 = new ImageIcon(this.getClass().getResource("/body-front.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/body-side.png")).getImage();
		Image img_cover = new ImageIcon(this.getClass().getResource("/seldat_icon.png")).getImage();
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(439, 145, 4, 360);
		frame.getContentPane().add(separator);
		
		JLabel front_img = new JLabel("");
		front_img.setIcon(new ImageIcon(img1));
		front_img.setBounds(453, 145, 258, 360);
		frame.getContentPane().add(front_img);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(721, 152, 2, 347);
		frame.getContentPane().add(separator_1);
		
		JLabel side_img = new JLabel("");
		side_img.setIcon(new ImageIcon(img2));
		side_img.setBounds(733, 145, 140, 360);
		frame.getContentPane().add(side_img);
		
		JLabel cover = new JLabel("");
		cover.setBounds(459, 11, 425, 80);
		cover.setIcon(new ImageIcon(img_cover));
		frame.getContentPane().add(cover);
		
		list_body_parts.add(txNeck);
		list_body_parts.add(txChest);
		list_body_parts.add(txBoobs);
		list_body_parts.add(txBelly);
		list_body_parts.add(txAss);
		list_body_parts.add(txHeigh);
		/*
		 * 
		 */
		//String data = "[125][127][128][129][130][131]";
		
		//list_body_parts.get(2).setText("128");;
	}
	int delay = 2500; //milliseconds
	  ActionListener taskPerformer = new ActionListener() {
	      public void actionPerformed(ActionEvent evt) {
	          if (txReceive.getText().startsWith("Not"))
	          {
	        	  timer.stop();
	          }
	          else if (txReceive.getText() == "Disconnected")
	          {
	        	  timer.stop();
	        	  txReceive.setText("Idle");
	          }
	          else 
	          {
	        	  timer.stop();
	        	  txReceive.setText("Ready");
	          }
	      }
	  };
	  
	Timer timer = new Timer(delay, taskPerformer);
	
	public void updateRecv(String data, int type){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      if (type == 1)
		      {
		    	  txReceive.setText(data);
		    	  timer.start();
		      }
		      else if(type == 0)
		      {
		    	  String[] body_part = data.split("\\[");
		      	  /*for (int ix = 0; ix < body_part.length; ix++){
		      		  list_body_parts.get(ix).setText(body_part[ix + 1].substring(0, body_part[ix + 1].length() - 1));
		      	  }*/
		      	  
		      	  txNeck.setText(body_part[1].substring(0, body_part[1].length() - 1));
		      	  txChest.setText(body_part[2].substring(0, body_part[2].length() - 1));
		      	  txBoobs.setText(body_part[3].substring(0, body_part[3].length() - 1));
		      	  txBelly.setText(body_part[4].substring(0, body_part[4].length() - 1));
		      	  txAss.setText(body_part[5].substring(0, body_part[5].length() - 1));
		      	  txHeigh.setText(body_part[6].substring(0, body_part[6].length() - 1));
		    	  txReceive.setText("Get Data Done");
		    	  timer.start();
		    	  
		      }
		      else
		    	  infoBox("Not support. Please check action", "Type Message Receive");
		    }
		  });
	}
	
	public static void Incr_Text (JFormattedTextField txbox)
	{
		txbox.setText(Integer.toString(Integer.parseInt(txbox.getText()) + 1)); 
	}
	
	public static void Sub_Text (JFormattedTextField txbox)
	{
		txbox.setText(Integer.toString(Integer.parseInt(txbox.getText()) - 1)); 
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	@Override
	public void passMsg(String msg, int set_type) {
			updateRecv(msg, set_type);
			
	}
	
	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean validateIP(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}
}
