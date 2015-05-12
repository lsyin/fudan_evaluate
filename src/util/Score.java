package util;

import com.mongodb.BasicDBObject;

public class Score extends BasicDBObject {
	
	private String userName;
	private String task;
	private String track;
	private String precision;
	private String recall;
	private String fb1;
	private String oov;
	private String description;
	private String submitTime;

	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getTask() {
		return task;
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getRecall() {
		return recall;
	}
	public void setRecall(String recall) {
		this.recall = recall;
	}
	public String getFb1() {
		return fb1;
	}
	public void setFb1(String fb1) {
		this.fb1 = fb1;
	}
	public String getOov() {
		return oov;
	}
	public void setOov(String oov) {
		this.oov = oov;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}
}
