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
import java.io.Console;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.UIManager;
import java.awt.Color;
//import java.awt.Component;

interface StringReceiver {
	void passMsg(String msg, Common.COMMAND set_type);
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
	private JButton btnConnect;
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
		int arr[] = null;
		if (arr == null) {
		  System.out.println("array is null");
		}
		list_body_parts = new ArrayList<JFormattedTextField>();
		Image img_ico = new ImageIcon(this.getClass().getResource("/Icon_SD.png")).getImage();
		frame = new JFrame();
		frame.setBounds(100, 100, 950, 640);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setIconImage(img_ico);
		frame.setTitle("Seldat Robot Manequinn");
		
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
		String type_reset[] = {"Min", "Max"};
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setBounds(10, 560, 46, 14);
		frame.getContentPane().add(lblStatus);
		
		txReceive = new JLabel("Idle");
		txReceive.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setLabelFor(txReceive);
		txReceive.setBounds(66, 560, 235, 14);
		frame.getContentPane().add(txReceive);

		Image img1 = new ImageIcon(this.getClass().getResource("/body-front.png")).getImage();
		Image img2 = new ImageIcon(this.getClass().getResource("/body-side.png")).getImage();
		Image img_cover = new ImageIcon(this.getClass().getResource("/seldat_icon.png")).getImage();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 934, 550);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Main Page", null, panel, null);
		
		JPanel ConnectionGroup = new JPanel();
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
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//tcpClient = new SocketClient (window, txAddress.getText(), 4321);
				if (btnConnect.getText() == "Connect")
				{
					com = new Communication(Common.TYPECONNECT.HDR_WIFI);
					if (com.Connect(window, txAddress.getText(), Integer.parseInt(txPort.getText())))
						{
							com.Send_Command_No_Data(Common.COMMAND.CMD_CONNECTION_REQUEST);
						}
					else
						infoBox("Cannot connect", "Socket Error");
				}
				else
				{
					com.Send_Command_No_Data(Common.COMMAND.CMD_DISCONNECT);
				}
			}
		});
		
		JLabel lblPort = new JLabel("Port:");
		
		txPort = new JFormattedTextField(formatter);
		lblPort.setLabelFor(txPort);
		txPort.setText("5000");
		txPort.setColumns(10);
		
		JLabel cover = new JLabel("");
		cover.setIcon(new ImageIcon(img_cover));
		BodyGroup = new JPanel();
		BodyGroup.setBorder(BorderFactory.createTitledBorder("Body Measurement"));
		
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
				txChest.getDocument().addDocumentListener(new DocumentListener(){
					public void changedUpdate(DocumentEvent e) {
					    warn();
					  }
					  public void removeUpdate(DocumentEvent e) {
					    warn();
					  }
					  public void insertUpdate(DocumentEvent e) {
					    warn();
					  }

					  public void warn() {
					     txBoobs.setText(txChest.getText());
					  }
				});
				
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
				
				JLabel lblMm = new JLabel("Unit: mm");
				lblMm.setHorizontalAlignment(SwingConstants.CENTER);
				
				JSeparator separator_2 = new JSeparator();
				separator_2.setOrientation(SwingConstants.VERTICAL);
				
				btnSetSize = new JButton("Set");
				btnSetSize.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (com != null)
						{
							String neck, chest, boobs, belly, ass, height;
							
							if(Objects.equals("0", txNeck.getText()))
								neck = "";
							else
								neck = txNeck.getText();
							if(Objects.equals("0", txChest.getText()))
								chest = "";
							else
								chest = txChest.getText();
							if(Objects.equals("0", txBoobs.getText()))
								boobs = "";
							else
								boobs = txBoobs.getText();
							if(Objects.equals("0", txBelly.getText()))
								belly = "";
							else
								belly = txBelly.getText();
							if(Objects.equals("0", txAss.getText()))   
								ass = "";
							else
								ass = txAss.getText();
							if(Objects.equals("0", txNeck.getText()))
								height = "";
							else
								height = txHeigh.getText();
							
							String body_size = "[" + neck  + "]"
											 + "[" + chest + "]"
											 + "[" + boobs + "]"
											 + "[" + belly + "]"
											 + "[" + ass   + "]"
											 + "[" + height + "]";
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
				JComboBox cbxReset = new JComboBox(type_reset);
				cbxReset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (com != null)
						{
							if (cbxReset.getSelectedIndex() == 0)
							{
								Reset_Component();
								com.Send_Command_One_Byte(Common.COMMAND.CMD_RESET_POSITION, (byte)0);
							}
							else
								com.Send_Command_One_Byte(Common.COMMAND.CMD_RESET_POSITION, (byte)1);
						}
					}
				});
				
				list_body_parts.add(txNeck);
				list_body_parts.add(txChest);
				list_body_parts.add(txBoobs);
				list_body_parts.add(txBelly);
				list_body_parts.add(txAss);
				list_body_parts.add(txHeigh);
				list_body_parts.clear();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Simulate", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(BodyGroup, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
						.addComponent(ConnectionGroup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(cover, GroupLayout.PREFERRED_SIZE, 425, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 579, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(cover, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addComponent(ConnectionGroup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
						.addComponent(BodyGroup, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JLabel front_img = new JLabel("");
		front_img.setIcon(new ImageIcon(img1));
		
		JLabel side_img = new JLabel("");
		side_img.setIcon(new ImageIcon(img2));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(front_img)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(side_img, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(side_img, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 368, Short.MAX_VALUE)
						.addComponent(front_img, GroupLayout.PREFERRED_SIZE, 368, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JButton btnReboot = new JButton("Reboot");
		btnReboot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (com != null)
					com.Send_Command_One_Byte(Common.COMMAND.CMD_REBOOT, (byte)4);
			}
		});
		GroupLayout gl_BodyGroup = new GroupLayout(BodyGroup);
		gl_BodyGroup.setHorizontalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNeck)
						.addComponent(txNeck, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblChest, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(txChest, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbBust, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(txBoobs, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbWaist, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(txBelly, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHip, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(txAss, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(txHeigh, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMm, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(de_neck)
						.addComponent(de_chest)
						.addComponent(de_bust)
						.addComponent(de_waist)
						.addComponent(de_hip)
						.addComponent(de_heigh))
					.addGap(10)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(add_neck)
						.addComponent(add_chest)
						.addComponent(add_bust)
						.addComponent(add_waist)
						.addComponent(add_hip)
						.addComponent(add_heigh))
					.addGap(44)
					.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 4, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING, false)
						.addComponent(cbxReset, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReboot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnSetSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnGetSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
		gl_BodyGroup.setVerticalGroup(
			gl_BodyGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_BodyGroup.createSequentialGroup()
					.addGroup(gl_BodyGroup.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addComponent(lblNeck)
							.addGap(3)
							.addComponent(txNeck, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblChest)
							.addGap(6)
							.addComponent(txChest, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbBust)
							.addGap(6)
							.addComponent(txBoobs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lbWaist)
							.addGap(6)
							.addComponent(txBelly, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblHip)
							.addGap(6)
							.addComponent(txAss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblHeight)
							.addGap(6)
							.addComponent(txHeigh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblMm))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGap(21)
							.addComponent(de_neck)
							.addGap(42)
							.addComponent(de_chest)
							.addGap(42)
							.addComponent(de_bust)
							.addGap(42)
							.addComponent(de_waist)
							.addGap(42)
							.addComponent(de_hip)
							.addGap(42)
							.addComponent(de_heigh))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGap(21)
							.addComponent(add_neck)
							.addGap(42)
							.addComponent(add_chest)
							.addGap(42)
							.addComponent(add_bust)
							.addGap(42)
							.addComponent(add_waist)
							.addGap(42)
							.addComponent(add_hip)
							.addGap(42)
							.addComponent(add_heigh))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGap(11)
							.addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 360, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_BodyGroup.createSequentialGroup()
							.addGap(195)
							.addComponent(cbxReset, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSetSize)
							.addGap(18)
							.addComponent(btnGetSize)
							.addGap(18)
							.addComponent(btnReboot)))
					.addGap(8))
		);
		BodyGroup.setLayout(gl_BodyGroup);
		GroupLayout gl_ConnectionGroup = new GroupLayout(ConnectionGroup);
		gl_ConnectionGroup.setHorizontalGroup(
			gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(lblIp)
						.addComponent(lblPort))
					.addGap(18)
					.addGroup(gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
						.addComponent(txAddress, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
						.addComponent(txPort, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addGap(46)
					.addComponent(btnConnect))
		);
		gl_ConnectionGroup.setVerticalGroup(
			gl_ConnectionGroup.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addGap(8)
					.addComponent(lblIp)
					.addGap(17)
					.addComponent(lblPort))
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addGap(5)
					.addComponent(txAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(txPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_ConnectionGroup.createSequentialGroup()
					.addGap(11)
					.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
		);
		ConnectionGroup.setLayout(gl_ConnectionGroup);
		panel.setLayout(gl_panel);
		
		//tabbedPane.addTab("Main Page", cover);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnProfile = new JMenu("Profile");
		mnFile.add(mnProfile);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnProfile.add(mntmSave);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnProfile.add(mntmLoad);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
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
	
	public void updateRecv(String data, Common.COMMAND type){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      if (type == Common.COMMAND.CMD_SET_POSITION)
		      {
		    	  txReceive.setText(data);
		    	  timer.start();
		      }
		      else if(type == Common.COMMAND.CMD_GET_POSITION)
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
		      else if (type == Common.COMMAND.CMD_CONNECTION_REQUEST)
		      {
		    	  Connect_View(true);
		    	  txReceive.setText(data);
		    	  timer.start();
		      }
		      else if (type == Common.COMMAND.CMD_DISCONNECT)
		      {
		    	  if (data == "Disconnected") {
		    		  com.Close();
		    		  com = null;
		    		  Connect_View(false);
		    		  txReceive.setText(data);
			    	  timer.start();
		    	  }
		    	  else if (data == "Close") {
		    		  txReceive.setText("Disconnected");
			    	  timer.start();
		    		  if(com != null){
		        		  com = null;
		        	  }
		    		  Connect_View(false);
		    	  }
		    	  else {
		    		  txReceive.setText(data);
			    	  timer.start();
		    	  }
		      }
		      else
		    	  infoBox("Not support. Please check action", "Type Message Receive");
		    }
		  });
	}

	private void Reset_Component()
	{
		txNeck.setText("0");
		txChest.setText("0");
		txBoobs.setText("0");
		txBelly.setText("0");
		txAss.setText("0");
		txHeigh.setText("0");
	}
	
	private void Connect_View (boolean connected)
	{
		if (connected)
		{
			btnConnect.setText("Disconnect");
		}
		else
		{
			btnConnect.setText("Connect");
			Reset_Component();
		}
	}
	
	public static void Incr_Text (JFormattedTextField txbox) {
		txbox.setText(Integer.toString(Integer.parseInt(txbox.getText()) + 1)); 
	}
	
	public static void Sub_Text (JFormattedTextField txbox) {
		txbox.setText(Integer.toString(Integer.parseInt(txbox.getText()) - 1)); 
	}
	
	public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	@Override
	public void passMsg(String msg, Common.COMMAND set_type) {
			updateRecv(msg, set_type);
			
	}
	
	private static final Pattern PATTERN = Pattern.compile(
	        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public static boolean validateIP(final String ip) {
	    return PATTERN.matcher(ip).matches();
	}
}