package chat;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

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
	private ArrayList<ObjectOutputStream>outputs;
	private String name;

	public WorkerRunnable(String name, JTextArea chatWindow, Socket clientSocket, ObjectInputStream input,ObjectOutputStream output,ArrayList<ObjectOutputStream>outputs) {
		this.clientSocket = clientSocket;
		this.chatWindow=chatWindow;
		this.output=output;
		this.input=input;
		this.outputs=outputs;
		this.name=name;
	}

	public void run() {
		try {
			while(true) {
				whileChatting();
			}
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
		finally {
			closeConnection();
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
		String message = "is now connected! ";
		sendMessage(name+": "+message);
		do{
			try{
				message = (String) input.readObject();
				//showMessage("\n" + message);
				sendMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("The user has sent an unknown object!");
			}
		}while(!message.equals("CLIENT - END"));
	}

	private void sendMessage(String message){
		try{
			Iterator<ObjectOutputStream>it=outputs.iterator();
			while(it.hasNext()) {
				output=it.next();
				output.writeObject(message);
				output.flush();
				showMessage(message);
			}
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
		showMessage(" Now connected to " + clientSocket.getInetAddress().getHostName());
	}
}