package chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SetConnection implements Runnable{

	private JTextArea chatWindow;
	protected Socket clientSocket = null;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private ArrayList<ClientHolder>clients;
	private String name;

	public SetConnection(JTextArea chatWindow,Socket clientSocket,ServerSocket server,ObjectOutputStream output,ObjectInputStream input,ArrayList<ClientHolder>clients) {

		this.chatWindow=chatWindow;
		this.clientSocket=clientSocket;
		this.output=output;
		this.input=input;
		this.server=server;
		this.clients=clients;

	}

	public void run() {

		try {
			while(true) {
				waitForConnection();
				setupStreams();
				try {
					name = (String) input.readObject();	//client will always send it's name when it connects 
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				ClientHolder currClient=new ClientHolder(name,output); //stores this client
				clients.add(currClient);
				showMessage(name+" is now connected!");
				new Thread(new WorkerRunnable(name,chatWindow,clientSocket,input,output,clients)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void waitForConnection() throws IOException{
		clientSocket = server.accept();
		showMessage(" \nNow connected to " + clientSocket.getInetAddress().getHostName());
	}
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(clientSocket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(clientSocket.getInputStream());
		showMessage("\n Streams are now setup \n");
	}
	
	private void showMessage(final String text){
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						chatWindow.append(text);
					}
				}
				);
	}
}
