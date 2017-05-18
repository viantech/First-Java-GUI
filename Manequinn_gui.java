package manequinn_src;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
//import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//import net.miginfocom.swing.MigLayout;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

interface StringReceiver {
	void passMsg(String msg);
}

public class Manequinn_gui implements StringReceiver {

	private JFrame frame;
	private JTextField txAddress;
	private JTextField txReceive;
	SocketClient tcpClient;
	private JTextField txSend;
	private static Manequinn_gui window;
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
		frame.setBounds(100, 100, 575, 332);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tcpClient = new SocketClient (window, txAddress.getText());
				if (btnConnect.getText() == "Connect")
				{
					if (tcpClient.Connect())
						btnConnect.setText("Disconnect");
					else
						infoBox("Cannot connect", "Socket Error");
				}
				else
				{
					tcpClient.Close();
					btnConnect.setText("Connect");
				}
			}
		});
		btnConnect.setBounds(221, 21, 89, 23);
		frame.getContentPane().add(btnConnect);
		
		txAddress = new JTextField();
		txAddress.setText("172.168.1.187");
		txAddress.setBounds(110, 22, 86, 20);
		frame.getContentPane().add(txAddress);
		txAddress.setColumns(10);
		
		txReceive = new JTextField();
		txReceive.setBounds(42, 186, 386, 96);
		frame.getContentPane().add(txReceive);
		txReceive.setColumns(10);
		
		txSend = new JTextField();
		txSend.setColumns(10);
		txSend.setBounds(42, 106, 154, 23);
		frame.getContentPane().add(txSend);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tcpClient.Send(txSend.getText());
			}
		});
		btnSend.setBounds(221, 109, 89, 23);
		frame.getContentPane().add(btnSend);
	}
	
	public void updateRecv(String data){
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		      // Here, we can safely update the GUI
		      // because we'll be called from the
		      // event dispatch thread
		    	txReceive.setText(data);
		    }
		  });
	}
	
	public static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }

	@Override
	public void passMsg(String msg) {
		updateRecv(msg);
	}
}
