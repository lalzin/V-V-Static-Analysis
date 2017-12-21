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

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataVar other = (DataVar) obj;
		if (line != other.line)
			return false;
		if (status != other.status)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (variableName == null) {
			if (other.variableName != null)
				return false;
		} else if (!variableName.equals(other.variableName))
			return false;
		return true;
	}
	
	
	
	
	
	
}
