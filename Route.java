import java.io.Serializable;

public class Route {
	// Following the pattern of the BusLinesDescription.txt we have the class Route with the following attributes.
	// will need to cast these in String types later on perhaps.
	private String routeCode;		// Unique ID for Bus's route.
	private String lineCode;  // lineCode from class BusLines.java or int??
	private String routeType;		// beginning to end
	private String routeDescription;
	private String lineID;
	Route( String lineCode, String routeCode, String routeDescription){
		this.routeCode = routeCode;
		this.lineCode = lineCode;
		this.routeDescription = routeDescription;
	}
	
	Route(String lineCode, String routeCode, String routeType, String routeDescription){
		this.routeCode = routeCode;
		this.lineCode = lineCode;
		this.routeType = routeType;
		this.routeDescription = routeDescription;
	}


	public String getRouteCode() {
		return routeCode;
	}
	
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	public String getRouteType() {
		return routeType;
	}
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	public String getRouteDescription() {
		return routeDescription;
	}
	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}
	String getLineID(){
		return lineID;
	}
	
	void setlineID(String lineID) {
		this.lineID =lineID;
	}
	
}
