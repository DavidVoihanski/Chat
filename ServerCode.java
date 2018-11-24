package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class ServerCode extends JFrame {

	private static JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private ArrayList<ClientHolder>clients;

	//constructor
	public ServerCode(JTextArea j){
		ServerCode.chatWindow=j;
        clients=new ArrayList<ClientHolder>();
	}

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
