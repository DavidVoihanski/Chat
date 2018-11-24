package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ClientListner implements Runnable {
	private String message;
	private ObjectInputStream input;
	private JTextArea chatWindow;
	private ObjectOutputStream output;
	private Socket clientSocket;

	public ClientListner(JTextField dst, ObjectInputStream input,ObjectOutputStream output,JTextArea chatWindow,Socket clientSocket) {
		this.input=input;
		this.output=output;
		this.clientSocket=clientSocket;
		this.chatWindow=chatWindow;

	}

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
	//while chatting with server
	private void whileChatting() throws IOException{
		do{
			try{
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("Unknown data received!");
			}
		}while(!message.equals("SERVER - END"));	
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
