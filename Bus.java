import java.util.Date;

public class Bus {
	/* Every bus has a specific BusLine, follows a specific Route and is on a certain Position.
	 * In order to identify the bus uniquely we will need to specify these attributes.
	 */
	private String line; // name
	private String lineID; 
	private String lineNumber;
	private String route; // code
	private String vehicleID;
	private Date dateSeen;
	//private String position;
	
	Bus(String line, String lineID, String lineNumber, String route, String vehicleID, Date dateSeen){
		this.line = line;
		this.setLineID(lineID);
		this.setLineNumber(lineNumber);
		this.route =route;
		this.setVehicleID(vehicleID);
		this.setDateSeen(dateSeen);
	}
	
	
	
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}
	
	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}



	public String getVehicleID() {
		return vehicleID;
	}



	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}



	public Date getDateSeen() {
		return dateSeen;
	}



	public void setDateSeen(Date dateSeen) {
		this.dateSeen = dateSeen;
	}





	
	
	
}
