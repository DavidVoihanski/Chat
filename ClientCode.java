package chat;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ClientCode extends JFrame{
	
	private JTextField userText;
	private String name;
	private static JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String message = "";
	private String serverIP;
	private Socket connection;
	
	//constructor
	public ClientCode(String host,String name, JTextArea chatWindow){
		super(name);
		this.name=name;
		ClientCode.chatWindow=chatWindow;
		serverIP = host;
	}
	
	//connect to server
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
			new Thread(new ClientListner(input,output,chatWindow,connection)).start();
		}catch(EOFException eofException){
			showMessage("\n Client terminated the connection");
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting connection... \n");
		connection = new Socket(InetAddress.getByName(serverIP), 6789);
		showMessage("Connection Established! Connected to: " + connection.getInetAddress().getHostName());
	}
	
	//set up streams
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n The streams are now set up! \n");
	}
	
//	//while chatting with server
//	private void whileChatting() throws IOException{
//		ableToType(true);
//		do{
//			try{
//				message = (String) input.readObject();
//				showMessage("\n" + message);
//			}catch(ClassNotFoundException classNotFoundException){
//				showMessage("Unknown data received!");
//			}
//		}while(!message.equals("SERVER - END"));	
//	}
//	
//	//Close connection
//	private void closeConnection(){
//		showMessage("\n Closing the connection!");
//		ableToType(false);
//		try{
//			output.close();
//			input.close();
//			connection.close();
//		}catch(IOException ioException){
//			ioException.printStackTrace();
//		}
//	}
	
	//send message to server
	public void sendMessage(String message){
		try{
			if(message!="") {
			output.writeObject("CLIENT - " + message);
			output.flush();
			showMessage("\n> "+name+": "+message);
			}
		}catch(IOException ioException){
			chatWindow.append("\n Oops! Something went wrong!");
		}
	}
	
	//update chat window
	private void showMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(message);
				}
			}
		);
	}
	
//	//allows user to type
//	private void ableToType(final boolean tof){
//		SwingUtilities.invokeLater(
//			new Runnable(){
//				public void run(){
//					userText.setEditable(tof);
//				}
//			}
//		);
//	}
	public Socket getConnection() {
		return this.connection;
	}
}