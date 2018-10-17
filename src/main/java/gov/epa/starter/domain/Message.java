package gov.epa.starter.domain;

import java.util.List;

public class Message {

	public static final int HELLO   = 0;
	public static final int GOODBYE = 1;

	private String          message;
	private int             status;
	private int		messageId;
	private int number;
	
	public Message(int messageId,String message, int status) {
		this.messageId=messageId;
		this.message=message;
		this.status=status;
		this.number=1;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public static Message doSomething(Message message) {
		return message;
	}

	public boolean isSomething(String msg,
			List<Object> list) {
		list.add( this );
		return this.message.equals( msg );
	}

	
	@Override
	public String toString() {
		return "id:" + this.messageId + " message:" +this.message + " status:" + this.status + " number: " + this.number;
	}
}
