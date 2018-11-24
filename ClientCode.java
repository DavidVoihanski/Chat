package chat;

import java.io.*;
import java.net.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class ClientCode extends JFrame{
	
	private String name;
	private static JTextArea chatWindow;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String serverIP;
	private Socket connection;
	private JTextField dst;
	
	//constructor
	public ClientCode(String host,String name,JTextField dst ,JTextArea chatWindow){
		super(name);
		this.name=name;
		this.dst=dst;
		ClientCode.chatWindow=chatWindow;
		serverIP = host;
	}
	
	//connect to server
	public void startRunning()throws Exception{
			connectToServer();
			setupStreams();
			sendNameToServer();
			new Thread(new ClientListner(dst,input,output,chatWindow,connection)).start();
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
	private void sendNameToServer() {
		try {
			output.writeObject(name);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String message){
		String dest=dst.getText();
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
	
	//update chat window
	public void showMessage(final String message){
		SwingUtilities.invokeLater(
			new Runnable(){
				public void run(){
					chatWindow.append(message);
				}
			}
		);
	}
	
	public Socket getConnection() {
		return this.connection;
	}
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