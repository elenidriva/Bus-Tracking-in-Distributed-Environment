
public class Route {
	// Following the pattern of the BusLinesDescription.txt we have the class Route with the following attributes.
	private int routeCode;		// Unique ID for Bus's route.
	private int lineCode;  // lineCode from class BusLines.java or int??
	private int routeType;		// beginning to end
	private String routeDescription;
	

	
	Route(int routeCode, int lineCode, int routeType, String routeDescription){
		this.routeCode = routeCode;
		this.lineCode = lineCode;
		this.routeType = routeType;
		this.routeDescription = routeDescription;
	}
	
	public int getRouteCode() {
		return routeCode;
	}
	
	public void setRouteCode(int routeCode) {
		this.routeCode = routeCode;
	}
	public int getLineCode() {
		return lineCode;
	}
	public void setLineCode(int lineCode) {
		this.lineCode = lineCode;
	}
	public int getRouteType() {
		return routeType;
	}
	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}
	public String getRouteDescription() {
		return routeDescription;
	}
	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}
	
}
