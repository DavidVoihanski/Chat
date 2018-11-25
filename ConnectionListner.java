package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
/**
 * This class is used a Thread to listen to every client's messages separately
 * Every time a new client joins a new Thread of this type is created
 * @author David
 *
 */
public class ConnectionListner implements Runnable {
	protected Socket clientSocket = null;
	private ObjectOutputStream output;				//can change to send to different clients
	private final ObjectOutputStream thisOutput;	//remembers this connectetion's output
	private final ObjectInputStream input;			//can never change, it listens to messages from the server
	private JTextArea chatWindow;
	private ArrayList<ClientHolder>clients;
	private String name;
	/**
	 * 
	 * @param name	The clients name
	 * @param chatWindow	The text area from the gui
	 * @param clientSocket	The socket connecting the server and the client
	 * @param input		Established input stream
	 * @param output	Established output stream
	 * @param clients	List of all connected clients of type ClientHolder, each containing the client's name and output stream
	 */
	public ConnectionListner(String name, JTextArea chatWindow, Socket clientSocket, ObjectInputStream input,ObjectOutputStream output,ArrayList<ClientHolder>clients) {
		this.clientSocket = clientSocket;
		this.chatWindow=chatWindow;
		this.thisOutput=output;
		this.output=output;
		this.input=input;
		this.clients=clients;
		this.name=name;
	}
	/**
	 * This method implementing the run() method for the Thread.
	 * Once called, starts listening to this client's messages
	 */
	public void run() {
		try {
			while(true) {
				whileChatting();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			disconnect();	//when disconnected for any reason
		}
	}
	/**
	 * update chatWindow
	 * @param text	The text we want to add
	 */
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append("\n"+ text);
					}
				}
				);
	}
	/**
	 * First notifies everyone that it joined and starts listening to this client's messages
	 * When a message is received, first checks if it says "showconnected" which send to 
	 * this client all of the names of the connected users and. 
	 * Or if it says "disconnect" which disconnect this client from the server.
	 * Then always looks for '&' separating the destination and the message and sends it only to the relevant clients
	 * @throws IOException
	 */
	private void whileChatting() throws IOException{

		String message = " is now connected! ";
		sendMessage(name + message,"All");  //notifies everyone this client connected
		do{
			try{
				message = (String) input.readObject();
				if(message.compareTo("showconnected")==0) showOnline(); 	//if "show online users" has been clicked prints all online users 
				else if(message.compareTo("disconnect")==0) throw new IOException("Client closed the connection");	//if disconnect has been clicked
				else {
					int endOfDest=message.indexOf('&');         //message is always sent with the dest at the start seperated by '&'
					String dest=message.substring(0, endOfDest);	//takes the dest section
					message=message.substring(endOfDest+1);			//sends the rest of the message
					sendMessage(message,dest);				//else sends the message
				}
			}catch(ClassNotFoundException classNotFoundException){
				showMessage("The user has sent an unknown object!");
			}
		}while(true);
	}
	/**
	 * Sends a message to client "dest"
	 * @param message	The message
	 * @param dest The client you want to message
	 */
	private void sendMessage(String message,String dest){

		Iterator<ClientHolder>it=clients.iterator();
		boolean wasSent=false;   //if the message was successfully sent
		boolean selfMessage=false;	//if tried to message itself
		if(clients.size()>1) {
			while(it.hasNext()) {                    //checks all connected users
				ClientHolder currClient=it.next();
				String clientName=currClient.getName();
				if(dest.compareTo(clientName)==0||dest.compareTo("All")==0)   //if dest is equal to the current client or if dest is 'All'
					if(clientName!=name)                   //to not message yourself   
						try{             
							wasSent=true;
							output=currClient.getOutput();   //takes the output stream from the current client
							output.writeObject(message);     //sends it
							output.flush();
						}catch(IOException ioException){				//if the connection was already closed 
							chatWindow.append("\n The connection to "+name+" was already closed");
							it.remove();								//Clients already left
						}
					else if(dest.compareTo("All")!=0){              
						message="Can't message yourself!";
						selfMessage=true;
						try {
							thisOutput.writeObject(message);   
							thisOutput.flush();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}     
					}
			}
			if(!wasSent&&!selfMessage) {
				try {
					thisOutput.writeObject("FAILED: "+dest+" is not connected!");
					thisOutput.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
			}
		}
		else {
			try {
				thisOutput.writeObject("You are the only one connected!");
				thisOutput.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * closes the sockets and streams
	 */
	private void closeConnection(){
		showMessage(name+ " left!");
		try{
			thisOutput.close(); //Closes the output path to the client
			input.close(); //Closes the input path to the server, from the client.
			clientSocket.close(); //Closes the connection between you can the client
		}catch(IOException ioException){
			ioException.printStackTrace();
		}
	}
	
	/**
	 * Prints all the online clients to this client
	 */
	private void showOnline() {
		Iterator<ClientHolder>it=clients.iterator();
		ClientHolder show;
		if(clients.size()>1) {							//if anyone else is connected
			while(it.hasNext()) {						//sends all of the names to this client
				show=it.next();
				String name=show.getName();
				try {
					thisOutput.writeObject("- "+name);	
					thisOutput.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else {
			try {
				thisOutput.writeObject("You are the only one connected!");	
				thisOutput.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * closes the connection and notifies everyone this client left
	 */
	private void disconnect() {
		sendMessage(name+" left!","All");
		Iterator<ClientHolder>it2=clients.iterator();
		ClientHolder find;
		String _name;
		while(it2.hasNext()) {				//finds this client in our list and removes it
			find=it2.next();
			_name=find.getName();
			if(_name==name)it2.remove(); 
		}
		closeConnection();				//closes the sockets and streams
	}
}
