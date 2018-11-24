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
	public void startRunning(){
		try{
			connectToServer();
			setupStreams();
			sendNameToServer();
			new Thread(new ClientListner(name,dst,input,output,chatWindow,connection)).start();
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
	private void showMessage(final String message){
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
}