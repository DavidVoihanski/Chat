package chat;

import java.io.ObjectOutputStream;

/**
 * Class used to make objects holding each clients name and output socket
 * @author David
 *
 */
public class ClientHolder {
	
	private String name;
	private ObjectOutputStream output;
	/**
	 * Constructor
	 * @param name	Client's name
	 * @param output	Client's output socket
	 */
	public ClientHolder(String name, ObjectOutputStream output) {
		this.name=name;
		this.output=output;
	}
	/**
	 * Sets the name
	 * @param name The name
	 */
	public void setName(String name) {
		this.name=name;
	}
	/**
	 * Sets the output stream
	 * @param output The stream
	 */
	public void setOutput(ObjectOutputStream output) {
		this.output=output;
	}
	/**
	 * 
	 * @return	Returns this clients name
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * 
	 * @return Returns this client's output stream
	 */
	public ObjectOutputStream getOutput() {
		return this.output;
	}
}
