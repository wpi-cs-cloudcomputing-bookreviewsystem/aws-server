package edu.wpi.cs.cloudcomputing.model;

public class Review {
	
	private String content;
	private Integer thumbUpNumber;
	private User reviewer;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getThumbUpNumber() {
		return thumbUpNumber;
	}
	public void setThumbUpNumber(Integer thumbUpNumber) {
		this.thumbUpNumber = thumbUpNumber;
	}
	public User getReviewer() {
		return reviewer;
	}
	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}
	
	
}
