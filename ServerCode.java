package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;
/**
 * The class which holds all of the relevant variables and objects to the server
 * Opens a server socket and start a Thread using SetConnection class to listen to client trying to connect
 * @author David
 *
 */
@SuppressWarnings("serial")
public class ServerCode extends JFrame {

	private static JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private ArrayList<ClientHolder>clients;

	/**
	 * constructor
	 * @param j The text are from the gui, updates when needed
	 */
	public ServerCode(JTextArea j){
		ServerCode.chatWindow=j;
        clients=new ArrayList<ClientHolder>();
	}
	/**
	 * This method starts the server
	 * SetConnection Thread is created to listen to clients trying to connect
	 * another Thread is created inside SetConnection to listen to every client's messages
	 */
	public void startRunning(){

		try{
			showMessage("Servers starting!");
			server = new ServerSocket(6789, 100); //6789 is a dummy port for testing, this can be changed. The 100 is the maximum people waiting to connect.
			new Thread(new SetConnection(chatWindow,connection,server,output,input,clients)).start();
		} catch (IOException ioException){
			ioException.printStackTrace();
		}
	}

	public void closeConnection(){
		showMessage("\n Closing Connections... \n");
		try{
			if(input!=null) output.close(); //Closes the output path to the client
			if(output!=null) input.close(); //Closes the input path to the server, from the client.
			if(connection!=null) connection.close(); //Closes the connection between you can the client
			if(server!=null) server.close();	//Closes the server socket

		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	//update chatWindow
	private void showMessage(final String text){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append(text);
			}
		});
	}

	public ServerSocket getServerSocket() {
		return this.server;
	}
}
