package edu.wpi.cs.cloudcomputing.messages;


public class ResponseMessage{
	private String status;
	private String content;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
