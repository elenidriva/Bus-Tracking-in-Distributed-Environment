
public class BusLines {
	// Following the pattern of the BusLinesDescription.txt we have the class BusLines with the following attributes.
	private int lineCode; // the ID of the busLine
	private String lineID; // busLineID, referenced as "topic" in the project
	private String lineDescription; // Latin characters must be used.
	
	BusLines(int lineCode, String lineID, String lineDescription){
		this.lineCode = lineCode;
		this.lineID = lineID;
		this.lineDescription = lineDescription;
		
	}
	// Getters, we might need setters as well.
	int getLineCode() {
		
		return lineCode;
	}
	
	String getLineID() {
		return lineID;
	}
	
	String getLineDescription() {
	return lineDescription;
	}
	
}