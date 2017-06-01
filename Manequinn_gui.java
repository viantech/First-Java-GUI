package manequinn_src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Image;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

interface StringReceiver {
	void passMsg(String msg, int set_type);
}

public class Manequinn_gui implements StringReceiver {

	private JFrame frame;
	private JTextField txAddress;
	//SocketClient tcpClient;
	Communication com;
	private JTextField txBelly;
	private static Manequinn_gui window;
	private JTextField txBoobs;
	private JButton btnSetSize;
	private JTextField txAss;
	private JTextField txChest;
	private JTextField txHeigh;
	private JPanel BodyGroup;
	private JTextField txPort;
	private JLabel txReceive;
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
		frame = new JFrame();
		frame.setBounds(100, 100, 910, 580);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		BodyGroup = new JPanel();
		BodyGroup.setBounds(25, 109, 290, 384);
		BodyGroup.setBorder(BorderFactory.createTitledBorder("Body Measurement"));
		frame.getContentPane().add(BodyGroup);
		
		JLabel lblChest = new JLabel("Chest");
		
		txChest = new JTextField();
		txChest.setHorizontalAlignment(SwingConstants.CENTER);
		txChest.setText("0");
		lblChest.setLabelFor(txChest);
		txChest.setColumns(10);
		
		JLabel lbBust = new JLabel("Bust");
		
		txBoobs = new JTextField();
		txBoobs.setHorizontalAlignment(SwingConstants.CENTER);
		txBoobs.setText("0");
		lbBust.setLabelFor(txBoobs);
		txBoobs.setColumns(10);
		
		JLabel lbWaist = new JLabel("Waist");
		
		txBelly = new JTextField();
		txBelly.setHorizontalAlignment(SwingConstants.CENTER);
		txBelly.setText("0");
		lbWaist.setLabelFor(txBelly);
		txBelly.setColumns(10);
		
		JLabel lblHip = new JLabel("Hips");
		
		txAss = new JTextField();
		txAss.setHorizontalAlignment(SwingConstants.CENTER);
		txAss.setText("0");
		lblHip.setLabelFor(txAss);
		txAss.setColumns(10);
		
		JLabel lblHeight = new JLabel("Heigh");
		
		txHeigh = new JTextField();
		txHeigh.setHorizontalAlignment(SwingConstants.CENTER);
		txHeigh.setText("0");
		lblHeight.setLabelFor(txHeigh);
		txHeigh.setColumns(10);
		Image img_add = new ImageIcon(this.getClass().getResource("/add.gif")).getImage();
		Image img_sub = new ImageIcon(this.getClass().getResource("/remove_correction.png")).getImage();
		
		JLabel add_heigh = new JLabel("");
		add_heigh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txHeigh);
			}
		});
		add_heigh.setIcon(new ImageIcon(img_add));
		
		JLabel add_chest = new JLabel("");
		add_chest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Incr_Text(txChest);
			}
		});
		add_chest.setIcon(new ImageIcon(img_add));
		
		JLabel add_bust = new JLabel("");
		add_bust.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Incr_Text(txBoobs);
			}
		});
		add_bust.setIcon(new ImageIcon(img_add));
		
		JLabel add_waist = new JLabel("");
		add_waist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txBelly);
			}
		});
		add_waist.setIcon(new ImageIcon(img_add));
		
		JLabel add_hip = new JLabel("");
		add_hip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Incr_Text(txAss);
			}
		});
		add_hip.setIcon(new ImageIcon(img_add));

		JLabel de_chest = new JLabel("");
		de_chest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txChest);
			}
		});
		de_chest.setIcon(new ImageIcon(img_sub));
		
		JLabel de_bust = new JLabel("");
		de_bust.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txBoobs);
			}
		});
		de_bust.setIcon(new ImageIcon(img_sub));

		JLabel de_waist = new JLabel("");
		de_waist.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txBelly);
			}
		});
		de_waist.setIcon(new ImageIcon(img_sub));
		
		JLabel de_hip = new JLabel("");
		de_hip.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txAss);
			}
		});
		de_hip.setIcon(new ImageIcon(img_sub));
		
		JLabel de_heigh = new JLabel("");
		de_heigh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Sub_Text(txHeigh);
			}
		});
		de_heigh.setIcon(new ImageIcon(img_sub));
		
		JLabel lblMm = new JLabel("mm");
		
		JLabel label = new JLabel("mm");
		
		JLabel label_1 = new JLabel("mm");
		
		JLabel label_2 = new JLabel("mm");
		
		JLabel label_3 = new JLabel("mm");
		
		btnSetSize = new JButton("Set");
		btnSetSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (com != null)
					{
						String body_size = "[" + txHeigh.getText() + "]"
										 + "[" + txChest.getText() + "]"
										 + "[" + txBoobs.getText() + "]"
										 + "[" + "10" + "]"
										 + "[" + txBelly.getText() + "]"
										 + "[" + txAss.getText() + "]";
						com.Send_Command_String(Common.COMMAND.CMD_SET_POSITION, body_size);
					
					}
			}
		});
		
		JButton btnGetSize = new JButton("Get");
		btnGetSize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (com != null)
					com.Send_Command_No_Data(Common.COMMAND.CMD_GET_POSITION);
			}
		});
		
		GroupLayout gl_BodyGroup = new GroupLayout(BodyGroup);
		gl_BodyGroup.setHorizontalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
						.addComponent(de_heigh)
						.addComponent(de_hip)
						.addComponent(de_waist)
						.addComponent(de_bust)
						.addComponent(de_chest))
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(lblChest, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbBust, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbWaist, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHip, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(txHeigh, 0, 0, Short.MAX_VALUE)
								.addComponent(txAss, 0, 0, Short.MAX_VALUE)
								.addComponent(txBelly, 0, 0, Short.MAX_VALUE)
								.addComponent(txBoobs, 0, 0, Short.MAX_VALUE)
								.addComponent(txChest, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
									.addComponent(label_3, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(add_heigh))
								.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
									.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(add_hip))
								.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(add_waist))
								.addGroup(gl_BodyGroup.createSequentialGroup()
									.addComponent(lblMm, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(add_chest))
								.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addComponent(add_bust)))))
					.addGap(93))
				.addGroup(Alignment.TRAILING, gl_BodyGroup.createSequentialGroup()
					.addContainerGap(114, Short.MAX_VALUE)
					.addComponent(btnSetSize, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(btnGetSize, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_BodyGroup.setVerticalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblChest)
					.addGap(6)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(de_chest)
						.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(gl_BodyGroup.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
									.addComponent(txChest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblMm))
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lbBust)
								.addGap(6))
							.addGroup(gl_BodyGroup.createSequentialGroup()
								.addComponent(add_chest)
								.addGap(42))))
					.addGap(6)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(txBoobs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label))
							.addGap(18)
							.addComponent(lbWaist))
						.addComponent(add_bust)
						.addComponent(de_bust))
					.addGap(6)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(txBelly, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1))
							.addGap(18)
							.addComponent(lblHip))
						.addComponent(add_waist)
						.addComponent(de_waist))
					.addGap(6)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(txAss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2))
							.addGap(18)
							.addComponent(lblHeight))
						.addComponent(add_hip)
						.addComponent(de_hip))
					.addGap(7)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(add_heigh)
						.addGroup(gl_BodyGroup.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
								.addComponent(txHeigh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_3))
							.addComponent(de_heigh)))
					.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSetSize)
						.addComponent(btnGetSize))
					.addContainerGap())
		);
		BodyGroup.setLayout(gl_BodyGroup);
		
		JPanel ConnectionGroup = new JPanel();
		ConnectionGroup.setBounds(25, 11, 278, 87);
		frame.getContentPane().add(ConnectionGroup);
		ConnectionGroup.setBorder(BorderFactory.createTitledBorder("Connection Properties"));
		
		JLabel lblIp = new JLabel("IP:");
		
		txAddress = new JTextField();
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
					if (com.Connect(window, txAddress.getText(), 5000))
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
		
		txPort = new JTextField();
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
						.addComponent(txAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txPort, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
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
		lblStatus.setBounds(10, 516, 46, 14);
		frame.getContentPane().add(lblStatus);
		
		txReceive = new JLabel("Idle");
		lblStatus.setLabelFor(txReceive);
		txReceive.setBounds(66, 516, 46, 14);
		frame.getContentPane().add(txReceive);

		Image img1 = new ImageIcon(this.getClass().getResource("/body-front.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/body-side.png")).getImage();
		Image img_cover = new ImageIcon(this.getClass().getResource("/seldat_icon.png")).getImage();
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(412, 109, 2, 403);
		frame.getContentPane().add(separator);
		
		JLabel front_img = new JLabel("");
		front_img.setIcon(new ImageIcon(img1));
		front_img.setBounds(424, 109, 258, 403);
		frame.getContentPane().add(front_img);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(692, 109, 2, 403);
		frame.getContentPane().add(separator_1);
		
		JLabel side_img = new JLabel("");
		side_img.setIcon(new ImageIcon(img2));
		side_img.setBounds(704, 109, 180, 403);
		frame.getContentPane().add(side_img);
		
		JLabel cover = new JLabel("");
		cover.setBounds(459, 11, 425, 80);
		cover.setIcon(new ImageIcon(img_cover));
		frame.getContentPane().add(cover);
	}
	int delay = 1500; //milliseconds
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
		    	  
		      }
		      else
		    	  infoBox("Not support. Please check action", "Type Message Receive");
		    }
		  });
	}
	
	public static void Incr_Text (JTextField txbox)
	{
		txbox.setText(Integer.toString(Integer.parseInt(txbox.getText()) + 1)); 
	}
	
	public static void Sub_Text (JTextField txbox)
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
}
