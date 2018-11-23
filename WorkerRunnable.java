package chat;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**

 */
public class WorkerRunnable implements Runnable{

	protected Socket clientSocket = null;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private JTextArea chatWindow;
	private ServerSocket server;
	private ArrayList<Socket>connected;

	public WorkerRunnable(Socket clientSocket,ServerSocket server, JTextArea chatWindow,ObjectOutputStream output,ObjectInputStream input,ArrayList<Socket> connected) {
		this.clientSocket = clientSocket;
		this.chatWindow=chatWindow;
		this.server=server;
		this.output=output;
		this.input=input;
		this.connected=connected;
	}

	public void run() {
		try {
			while(true) {
			waitForConnection();
			setupStreams();
			whileChatting();
			}
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
		finally {
			//closeConnection();
		}
			
	}
	//update chatWindow
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append("\n"+ text);
					}
				}
				);
	}
	private void whileChatting() throws IOException{
		String message = " You are now connected! ";
		sendMessage(message);
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("The user has sent an unknown object!");
			}
		}while(!message.equals("CLIENT - END"));
	}

	private void sendMessage(String message){
		try{
			output.writeObject("SERVER - " + message);
			output.flush();
			showMessage("\nSERVER -" + message);
		}catch(IOException ioException){
			chatWindow.append("\n ERROR: CANNOT SEND MESSAGE, PLEASE RETRY");
		}
	}
	public void closeConnection(){
		showMessage("\n Closing Connections... \n");
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			clientSocket.close(); //Closes the connection between you can the client
			server.close();
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	//get stream to send and receive data
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());
		showMessage("\n Streams are now setup \n");
	}
	//waits for someone to connect
	private void waitForConnection() throws IOException{
		showMessage(" Waiting for someone to connect... \n");
		clientSocket = server.accept();
		connected.add(clientSocket);
		showMessage(" Now connected to " + clientSocket.getInetAddress().getHostName());
	}
}