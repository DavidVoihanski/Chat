package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 * This class is used as a Thread to Listen to messaged from the server
 * @author David
 * 
 */
public class ClientListner implements Runnable {
	private String message;
	private ObjectInputStream input;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private Socket clientSocket;
	/**
	 * Constructor
	 * @param input	This client's input stream
	 * @param output	This client's output stream
	 * @param chatWindow	The main text area from the gui
	 * @param clientSocket	The socket connecting between the server and this client
	 */
	public ClientListner(ObjectInputStream input,ObjectOutputStream output,JTextArea chatWindow,Socket clientSocket) {
		this.input=input;
		this.output=output;
		this.clientSocket=clientSocket;
		this.chatWindow=chatWindow;

	}
	/**
	 * The method implementing the run() method for this Thread.
	 * Once called, listens to messages from the server to this client
	 */
	public void run() {
		try {
			whileChatting();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			closeConnection();
		}
	}
	/**
	 * Listens to messages from the server and updates the gui using the showMessage method
	 */
	private void whileChatting() throws IOException{
		do{
			try{
				message = (String) input.readObject();	//waits for messages
				showMessage("\n" + message);			//updates the gui
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("Unknown data received!");
			}
		}while(true);
	}
	/**
	 * Updates chat window 
	 * @param message The message you wish to add
	 */
	private void showMessage(final String message){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(message);
					}
				}
				);
	}
	/**
	 * Closes the socket and streams 
	 */
	public void closeConnection(){
		showMessage("\n Closing Connections... \n");
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			clientSocket.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}
