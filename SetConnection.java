package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 * This class is used as a Thread to listen to clients trying to connect to the server on the server socket we opened.
 * Once a client joined, creates a new Thread using the ConnectionLister class to listen to every this client's messages
 * @author David
 */
public class SetConnection implements Runnable{

	private JTextArea chatWindow;
	protected Socket clientSocket = null;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private ArrayList<ClientHolder>clients;
	private String name;
	/**
	 * Constructor
	 * @param chatWindow	The test area from the gui
	 * @param clientSocket	The socket for the connection between the server and the client
	 * @param server		the sever socket we opened
	 * @param output		output stream
	 * @param input			input stream
	 * @param clients		ArrayList of ClientHolder type, holds output streams and names of clients
	 */
	public SetConnection(JTextArea chatWindow,Socket clientSocket,ServerSocket server,ObjectOutputStream output,ObjectInputStream input,ArrayList<ClientHolder>clients) {

		this.chatWindow=chatWindow;
		this.clientSocket=clientSocket;
		this.output=output;
		this.input=input;
		this.server=server;
		this.clients=clients;

	}
	/**
	 * This method starts listening to clients trying to connect to the socket 'server'
	 * Once connected and this client to out ClientHolder arraylist and starts a new Thread to listen to this client's messages
	 */
	public void run() {
		try {
			while(true) {
				waitForConnection();
				setupStreams();
				try {
					name = (String) input.readObject();	//client will always send it's name when it connects 
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				while(exists(name)) {	//while the name already exists, adds '*' to it
					name=name+"*";
				}
				ClientHolder currClient=new ClientHolder(name,output); //stores this client
				clients.add(currClient);
				showMessage(name+" is now connected!");
				new Thread(new ConnectionListner(name,chatWindow,clientSocket,input,output,clients)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Waits for a client to connect to the server socket
	 * @throws IOException
	 */
	private void waitForConnection() throws IOException{
		clientSocket = server.accept();
		showMessage(" \nNow connected to " + clientSocket.getInetAddress().getHostName());
	}
	/**
	 * Sets up streams
	 * @throws IOException
	 */
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());
		showMessage("\n Streams are now setup \n");
	}
	/**
	 * Updates the server's gui main text area
	 * @param text	test we want to add
	 */
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(text);
					}
				}
				);
	}
	/**
	 * checks if this name already exists
	 * @param name The name we want to check if already exists
	 * @return	Returns true if already exists and false otherwise
	 */
	private boolean exists(String name) {
		Iterator<ClientHolder>it3=clients.iterator();
		ClientHolder _currClient;
		while(it3.hasNext()) {
			_currClient=it3.next();
			if(_currClient.getName().compareTo(name)==0)return true; //if found equal name
		}
		return false;  //if not found
	}
}
