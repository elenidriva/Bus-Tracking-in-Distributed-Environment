import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;



public class Publisher implements Serializable {
	
	String message = null;
	private static int port = 5678;
	
	

	// We will need these to be static.
	static ArrayList<Route> routes = new ArrayList<Route>();
	static ArrayList<BusLines> busLines = new ArrayList<BusLines>();
	static ArrayList<BusPosition> busPositions = new ArrayList<BusPosition>();
	static ArrayList<Value> values = new ArrayList<>();
	private static ArrayList<Topic> busInfo = new ArrayList<>();
    private static HashMap<Topic, ArrayList<Value>> serverResults = new HashMap<>();


	public static void main (String[] args) throws IOException, ParseException, InterruptedException {
		//int serverPort = getRandomPort(4444,9999);
		Broker.createTopic(busInfo);
		createRoutes(routes);
		createBusLines(busLines);
		createBus();
		
		ServerSocket socket = new ServerSocket(port, 100);
			System.out.println("New client thread created");
		       try {
		            while (true) {
		                Socket connected = socket.accept();
		    			Thread serverThread = new Thread(new ConnectionWithBroker(connected));
		    			
		    			System.out.println("New Broker with adrdress" + " " + connected.getInetAddress() + " has connected on port: " + connected.getLocalPort() + connected.getPort()); // it's the default port they always connect there first
		    			serverThread.start();
		            }
		        } catch (IOException e) {
		            throw new RuntimeException("cannot connect", e);
		        }
			}
		//}
		

	public static class ConnectionWithBroker implements Runnable { // this is our push function
        private static Socket connected;

        ConnectionWithBroker(Socket connected) {
            ConnectionWithBroker.connected = connected;
        }

        @Override
    	public void run() {
    		// TODO Auto-generated method stub
         	try {
                String broker;
                Object fromServer;
                ObjectInputStream toServer = new ObjectInputStream(connected.getInputStream());
                fromServer = toServer.readObject();
                if(fromServer.toString().startsWith("1") || fromServer.toString().startsWith("2") || fromServer.toString().startsWith("3")) {
                	
                    broker = fromServer.toString();
                    System.out.println(broker + " has connected to the Server.");

                    for (Topic topic: busInfo){
                        ArrayList<Value> createResults = new ArrayList<>();
                       // System.out.println("toppic: " +topic.getLineID());
                        for (Value info : values) {
                        	
                        	if (topic.getLineID().equals(info.getBus().getLineID())) createResults.add(info); // !
                        	//System.out.println("toppic: " +topic.getLineID());
                        	//System.out.println("to be sent from publish - Lat: " +info.getBus().getLatitude());
                        }
                        serverResults.put(topic, createResults);
                    }

                }
                System.out.println("Sending information to: " + connected);
                ObjectOutputStream out = new ObjectOutputStream(connected.getOutputStream());
                out.writeObject(serverResults);
    			out.writeObject("Stop");
                connected.close();

        	} catch (IOException | ClassNotFoundException e) {
    		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
    	
    	}		

    }
    
	// We will create Routes here
	private static void createRoutes(ArrayList<Route> routes) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("RouteCodesNew.txt"));
        String str;
        String columns[] = new String[4];
        int i = 0;
        while ((str = in.readLine())!= null) {
        	i = 0;
            for (String word : str.split(",")) {
                columns[i] = word;
                i++;
            }
         
            //need to convert from string to int for the columns int routeCode, lineCode, routeType;
            // valueOf-> Integer Object or parseInt -> int
            routes.add( new Route(columns[1], columns[0], columns[3]) );
        }
        System.out.println("Routes List is ready!");
        in.close();
        in = new BufferedReader(new FileReader("busLinesNew.txt"));
        str = in.readLine();
        while(str != null){
            int j = 0;
            for (String word : str.split(",")) {
                columns[j] = word;
                i++;
            }
            // string problem
            for (Route route:routes) if(route.getLineCode().equals(columns[0])) route.setLineCode(columns[1]);
            str = in.readLine();
        }
        in.close();
        System.out.println("Matched routes with busLines!");
        /*
        // Testing the Output. Works accordingly!
         System.out.println("Routes List");
         for(int j = 0; j < routes.size(); j++) {
        System.out.println(routes.get(j).getRouteCode() +" "+ routes.get(j).getLineCode()+ " " + routes.get(j).getRouteType() + " "+  routes.get(j).getRouteDescription() );
        System.out.println(routes.get(j).getRouteCode());
        System.out.println(routes.get(j).getLineCode());
        System.out.println(routes.get(j).getRouteType());
        System.out.println(routes.get(j).getRouteDescription());
        }
        */
        
	}
	
	// Here we need to see which Route a BUS follows.
	private static void createBusLines(ArrayList<BusLines> busLines) throws NumberFormatException, IOException {
	
        BufferedReader in = new BufferedReader(new FileReader("BusLinesNew.txt"));
        String str;
        String columns[] = new String[3];
        int i = 0;
        while ((str = in.readLine())!= null) {
        	i = 0;
            for (String word : str.split(",")) {
                columns[i] = word;
                i++;
            }
            //need to convert from string to int for the columns int routeCode, lineCode, routeType;
            // valueOf-> Integer Object or parseInt -> int
            busLines.add( new BusLines (columns[0], columns[1].trim(), columns[2].trim()));
 
           /* 
            System.out.println("busLines List");
            for(int j = 0; j < busLines.size(); j++) {
            	 System.out.println(busLines.get(j).getLineCode() + " " + busLines.get(j).getLineID() + " " + busLines.get(j).getLineDescription()); 	
           System.out.println(busLines.get(j).getLineCode());
           System.out.println(busLines.get(j).getLineID());
           System.out.println(busLines.get(j).getLineDescription());
           }
           
            */
        }
        in.close();
        System.out.println("BusLines List is ready!");
		
	}

	private static void createBus() throws IOException, ParseException {
	//	BusPosition(int lineCode, int routeCode, int vehicleID, double latitude, double longitude, Date timeStampOfBusPos){
        BufferedReader in = new BufferedReader(new FileReader("busPositionsNew.txt"));
        String str;
        String columns[] = new String[6];
        int i = 0;
        while ((str = in.readLine())!= null) {
        	i = 0;
            for (String word : str.split(",")) {
                columns[i] = word;
                i++;
            }
            String dateSeen = columns[5];
            // https://www.mkyong.com/java/how-to-convert-string-to-date-java/  parsing the Date 
            // https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
            DateFormat formatter = new SimpleDateFormat("MMM d yyyy HH:mm:ss:SSSa");
            Date date = formatter.parse(dateSeen);
            for(Route route: routes) {
            	
            	if(route.getLineCode().equals(columns[0]) && route.getRouteCode().equals(columns[1])){
            		for(BusLines busline: busLines) {
            			if (busline.getLineCode().equals(route.getLineCode())){
                    		Bus bus = new Bus(busline.getLineID(),columns[0], columns[1], columns[2], columns[3],columns[4], date);
                    		values.add(new Value(bus));
            			}
            		}
            		//values will return tuples of the following format bus, lat, long
            	}
            }

            
           }
        in.close();
        /*
        System.out.println("Bus List is ready!");
        System.out.println("Values List is ready!");
        for(int j = 0; j < values.size(); j++) {
        	System.out.println("value" + values.get(j));	
       System.out.println("Latitude:" + values.get(j).getBus().getLatitude());
       System.out.println("Long:" + values.get(j).getBus().getLongitude());
       System.out.println("LineID:" + values.get(j).getBus().getLineID());
       System.out.println("Descr:" + values.get(j).getBus().getRouteCode());
        */
        
        /*
        System.out.println("busPositions List");
        for(int j = 0; j < busPositions.size(); j++) {
        System.out.println(busPositions.get(j).getLineCode() + " " + busPositions.get(j).getRouteCode() + " " + busPositions.get(j).getVehicleID() + " " + busPositions.get(j).getLatitude()+ " " + busPositions.get(j).getLongitude()+ " " + busPositions.get(j).getTimeStampOfBusPos()); 	
       System.out.println(busPositions.get(j).getLineCode());
       System.out.println(busPositions.get(j).getRouteCode());
       System.out.println(busPositions.get(j).getVehicleID());
       System.out.println(busPositions.get(j).getLatitude());
       System.out.println(busPositions.get(j).getLongitude());
       System.out.println(busPositions.get(j).getTimeStampOfBusPos());
       */
	
	}
}
