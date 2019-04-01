
public class Bus {
	/* Every bus has a specific BusLine, follows a specific Route and is on a certain Position.
	 * In order to identify the bus uniquely we will need to specify these attributes.
	 */
	private BusLines line;
	private Route route;
	private BusPosition position;
	
	Bus(BusLines line, Route route, BusPosition position){
		this.line = line;
		this.route = route;
		this.position = position;
	}
	
	
	
	public BusLines getLine() {
		return line;
	}
	public void setLine(BusLines line) {
		this.line = line;
	}
	public Route getRoute() {
		return route;
	}
	public void setRoute(Route route) {
		this.route = route;
	}
	public BusPosition getPosition() {
		return position;
	}
	public void setPosition(BusPosition position) {
		this.position = position;
	}
	
	
	
}
