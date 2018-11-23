package chat;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ServerCode extends JFrame {

	private static JTextArea chatWindow;
	private JTextField userText;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private ArrayList<Socket>connected;
	
	//constructor
	public ServerCode(JTextArea j){
		ServerCode.chatWindow=j;
		connected=new ArrayList<Socket>();
	}

	public void startRunning(){
		
		try{
			showMessage("ServerStarting");
			server = new ServerSocket(6789, 100); //6789 is a dummy port for testing, this can be changed. The 100 is the maximum people waiting to connect.
				new Thread(new WorkerRunnable(connection,server,chatWindow,output,input,connected)).start(); 
		} catch (IOException ioException){
			ioException.printStackTrace();
		}
	}

	//during the chat conversation
	private void whileChatting() throws IOException{
		String message = " You are now connected! ";
		sendMessage(message);
		ableToType(true);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("The user has sent an unknown object!");
			}
		}while(!message.equals("CLIENT - END"));
	}

	public void closeConnection(){
		showMessage("\n Closing Connections... \n");
		ableToType(false);
		try{
			if(input!=null) output.close(); //Closes the output path to the client
			if(output!=null) input.close(); //Closes the input path to the server, from the client.
			if(connection!=null) connection.close(); //Closes the connection between you can the client
			if(server!=null) server.close();
			
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}

	//Send a mesage to the client
	private void sendMessage(String message){
		try{
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nSERVER -" + message);
		}catch(IOException ioException){
			chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}

	//update chatWindow
	private void showMessage(final String text){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				chatWindow.append("\n" + text);
			}
		});



	}

	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
				);
	}
	public ServerSocket getServerSocket() {
		return this.server;
	}

}
