package chat;

import java.io.ObjectOutputStream;

public class ClientHolder {
	
	private String name;
	private ObjectOutputStream output;

	public ClientHolder(String name, ObjectOutputStream output) {
		this.name=name;
		this.output=output;
	}
	
	public void setName(String name) {
		this.name=name;
	}
	public void setOutput(ObjectOutputStream output) {
		this.output=output;
	}
	public String getName() {
		return this.name;
	}
	public ObjectOutputStream getOutput() {
		return this.output;
	}
}
