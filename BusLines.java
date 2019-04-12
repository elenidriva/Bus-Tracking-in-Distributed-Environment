import java.io.Serializable;
public class BusLines { // busLine = topic
	// Following the pattern of the BusLinesDescription.txt we have the class BusLines with the following attributes.
	private String lineCode; // the ID of the busLine, unique
	private String lineID; // busLineID, referenced as "topic" in the project
	private String lineDescription; // Latin characters must be used.
	//	lineCode, lineID, lineDescription
	// 1219,		036,	KATECHAKI - KYPSELI
	// 1220,		036,	KATECHAKI - KYPSELI
	BusLines(String lineCode, String lineID, String lineDescription){
		this.lineCode = lineCode;
		this.lineID = lineID;
		this.lineDescription = lineDescription;
		
	}
	// Getters, we might need setters as well.
	String getLineCode() {
		
		return lineCode;
	}
	
	String getLineID() {
		return lineID;
	}
	
	String getLineDescription() {
	return lineDescription;
	}
	

	
}