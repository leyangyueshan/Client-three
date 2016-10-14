import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements Runnable{
	private JTextField jtf = new JTextField();
	private JTextArea jta = new JTextArea();
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	private int client1 = 1;
	private int client2 = 2;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Enter ClientText:"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		jtf.addActionListener(new TextFieldListener());
		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		try {
			// Socket socket = new Socket("localhost", 8000);
			Socket socket = new Socket("10.170.35.23", 8000);
			// Socket socket = new Socket("网址",8000);
			fromServer = new DataInputStream(socket.getInputStream());
			toServer = new DataOutputStream(socket.getOutputStream());

		} catch (IOException ex) {
			jta.append(ex.toString() + "\n");

		}
		// Thread thread = new Thread(this);
		// thread.start();
		new Thread(this).start();
	}

	public void run() {
		try {
			// int client = fromServer.readInt();
			// jta.append("I am "+client+"\n");
			while (true) {
				String area = fromServer.readUTF();
				jta.append("client2 says:" + area + "\n");
			}

		} catch (Exception ex) {

		}

	}

	private class TextFieldListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String a = jtf.getText().trim();
			// double radius =Double.parseDouble(jtf.getText().trim());

			try {
				toServer.flush();
				toServer.writeUTF(a);
				//String area = fromServer.readUTF();
				//jta.append("client2 says:" + area + "\n");
				jta.append("me:" + a + "\n");
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

		}

	}
}
