package game.net;

public class OnEvent {

	String socketName;
	OnCallback callback;
	
	public OnEvent(String socketName, OnCallback callback) {
		this.socketName = socketName;
		this.callback = callback;
	}
	
}
