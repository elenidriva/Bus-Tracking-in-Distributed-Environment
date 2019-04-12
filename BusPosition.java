import java.util.Date;
import java.io.Serializable;
public class BusPosition {
	// Following the pattern of the BusLinesDescription.txt we have the class BusPosition with the following attributes.
private String lineCode;
private int routeCode;
private int vehicleID;
private double latitude;
private double longitude;
private Date timeStampOfBusPos;
	
	BusPosition(String lineCode, int routeCode, int vehicleID, double latitude, double longitude, Date timeStampOfBusPos){
	this.setLineCode(lineCode);
	this.setRouteCode(routeCode);
	this.setVehicleID(vehicleID);
	this.setLatitude(latitude);
	this.setLongitude(longitude);
	this.setTimeStampOfBusPos(timeStampOfBusPos);
	
	}


	public String getLineCode() {
		return lineCode;
	}


	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}


	public int getRouteCode() {
		return routeCode;
	}


	public void setRouteCode(int routeCode) {
		this.routeCode = routeCode;
	}


	public int getVehicleID() {
		return vehicleID;
	}


	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public Date getTimeStampOfBusPos() {
		return timeStampOfBusPos;
	}


	public void setTimeStampOfBusPos(Date timeStampOfBusPos) {
		this.timeStampOfBusPos = timeStampOfBusPos;
	}

}