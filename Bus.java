import java.util.Date;
import java.io.Serializable;
public class Bus implements Serializable {
	/* Every bus has a specific BusLine, follows a specific Route and is on a certain Position.
	 * In order to identify the bus uniquely we will need to specify these attributes.
	 */
	private String lineCode; // name
	private String lineID; 
	private String lineNumber;
	private String routeCode; // code
	private String vehicleID;
	private String latitude;
	private String longitude;
	private Date timestampOfBusPosition;
	//private String position;
	
	Bus(String lineID, String lineCode, String routeCode, String vehicleID, String latitude, String longitude, Date timestampOfBusPosition){
		this.setLineID(lineID);
		this.setLineCode(lineCode);
		this.setRouteCode(routeCode);
		this.setVehicleID(vehicleID);
		this.setLatitude(latitude);
		this.setLongitude(longitude);
		this.setTimestampOfBusPosition(timestampOfBusPosition);
		
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
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

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(String vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getTimestampOfBusPosition() {
		return timestampOfBusPosition;
	}

	public void setTimestampOfBusPosition(Date timestampOfBusPosition) {
		this.timestampOfBusPosition = timestampOfBusPosition;
	}
	




	
}
