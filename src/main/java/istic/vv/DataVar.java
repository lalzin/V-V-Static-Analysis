package istic.vv;

public class DataVar {

	private String value;
	public enum STATUS {
		  OK,
		  WARNING,
		  ALERT	
	}
	
	
	
	public DataVar(String value, STATUS status) {
		this.value = value;
		this.status = status;
	}
	private STATUS status;
	
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
	
}
