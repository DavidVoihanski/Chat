package chat;

import java.io.*;
import java.net.*;

import javax.swing.*;

/**
 * This class is is the main client object, holds all of the relevant variables and objects
 * Once startRunning() is called it connects to the server and creates a new Thread which listens to 
 * messages from the server.
 * @author David
 *
 */
@SuppressWarnings("serial")
public class ClientCode extends JFrame{

	private String name;
	private static JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	private Socket connection;
	private JTextField dst;

	/**
	 * Constructor
	 * @param host	The server ip you wish to connect to, taken from gui
	 * @param name	The name of this client, taken from gui	
	 * @param dst	The dst test field from gui , the client need to be able to change destinations for messages
	 * @param chatWindow	The chatWindow text area, updates when needed
	 */
	public ClientCode(String host,String name,JTextField dst ,JTextArea chatWindow){
		super(name);
		this.name=name;
		this.dst=dst;
		ClientCode.chatWindow=chatWindow;
		serverIP = host;
	}

	/**
	 * Starts the client running
	 * Once the socket and streams are set up, creates a Thread using the ClientLister class to 
	 * listen to messages from the server
	 * @throws Exception
	 */
	public void startRunning()throws Exception{
		connectToServer();
		setupStreams();
		sendNameToServer();
		new Thread(new ClientListner(dst,input,output,chatWindow,connection)).start();
	}

	/**
	 * Connects to the server
	 * Takes the ip from our gui port can be changed 
	 * @throws IOException
	 */
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789); //(ip,port)
		showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}

	/**
	 * Sets up new streams from our socket
	 * @throws IOException
	 */
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n The streams are now set up! \n");
	}
	/**
	 * Sends this clients name to the server
	 */
	private void sendNameToServer() {
		try {
			output.writeObject(name);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Sends a message to the server.
	 * Takes dest from the gui and "sticks" it to the start of our message.
	 * When the server gets the message it always looks for '&' and seperating the message and the destination
	 * @param message
	 */
	public void sendMessage(String message){
		String dest=dst.getText();
		if(dest.contains("&")) {
			showMessage("Destination can't contain '&'!");
			return;
		}
		try{
			if(message.length()>0) {
				output.writeObject(dest+"&"+name+": " + message);
				output.flush();
				if(dest!=name)showMessage("\n> "+name+": "+message);  //to avoid double messaging
			}
		}catch(IOException ioException){
			chatWindow.append("\n Oops! Something went wrong!");
		}
	}

	/**
	 * Update chat window.
	 * @param message the message we wan't to send
	 */
	public void showMessage(final String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				}
				);
	}

	/**
	 * 
	 * @return returns this Socket
	 */
	public Socket getConnection() {
		return this.connection;
	}
	/**
	 * closes Socket and streams
	 */
	public void closeConnection(){
		showMessage("\n Closing Connections... \n");
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			connection.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	/**
	 * sends a command to the server
	 * not meant to be sent to other clients!
	 */
	public void sendCommand(String command) {
		try {
			output.writeObject(command);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}