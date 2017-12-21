package istic.vv;

/**
 * Classe correspondant aux traces d'appels effectués pour l'analyse des NPE.
 * Ces objets sont envoyés a l'utilisateur, dans une liste.
 * Cette liste doit être vu comme un rapport d'analyse pour l'utilisateur.
 * 
 * Le type DataVar est structuré comme suit :
	   * 	VariableName : la variable appelée, concerné.
	   * 	Value : La valeur de la variable concerné
	   * 	Status : 3  valeurs enumération : 
	   * 					Alert (le NPE est certains d'être déclenché)
	   * 					Warning (Le NPE est potentiellement dangereux. Impossible de le determiner.
	   * 					OK (L'appel est safe, pas de NPE potentiel.
	   * 	Line : La ligne concerné dans le programme analysé.
 *
 * @author Simon LEDOUX-LEVIN / Alan MARZIN
 *
 */
public class DataVar {

	private String variableName;
	private String value;
	private int line;
	public enum STATUS {
		  OK,
		  WARNING,
		  ALERT
	}
	
	
	/**Constructeur d'un dataVar
	 * 
	 * 
	 * @param value la valeur de la variable devant etre stocké
	 * @param status le status de l'appel. Soit ALERT, WARNING,OK
	 * @param line la ligne concerné par l'appel
	 * @param variableName le nom de la variable appelée.
	 */
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
