package br.com.seg.econotaxi.socket.message;

public class SocketMessage {

	private String message;
	
	public SocketMessage() {
		// Construtor default
	}
	
	public SocketMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
