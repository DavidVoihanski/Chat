package chat;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.Socket;

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
	private JTextField userText;

	public WorkerRunnable(Socket clientSocket, JTextArea chatWindow,JTextField userText,ObjectOutputStream output,ObjectInputStream input) {
		this.clientSocket = clientSocket;
		this.chatWindow=chatWindow;
		this.userText=userText;
		this.output=output;
		this.input=input;
	}

	public void run() {
		try {

			whileChatting();
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
						chatWindow.append(text);
					}
				}
				);
	}
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
	private void ableToType(final boolean tof){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						userText.setEditable(tof);
					}
				}
				);
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
		ableToType(false);
		try{
			output.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			clientSocket.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
}