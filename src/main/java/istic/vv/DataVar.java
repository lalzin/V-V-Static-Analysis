package istic.vv;

public class DataVar {

	private String variableName;
	private String value;
	private int line;
	public enum STATUS {
		  OK,
		  WARNING,
		  ALERT,
		  UNDEFINED
	}
	
	
	
	public DataVar(String value, STATUS status, int line, String variableName) {
		this.variableName = variableName;
		this.value = value;
		this.status = status;
		this.line = line;
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
	public int getLine() {
		return line;
	}
	public void setLine(int line) {
		this.line = line;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	
	
	
	
	
}
