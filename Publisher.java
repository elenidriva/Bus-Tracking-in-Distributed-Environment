import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Publisher {
	// We will store all the data we've made so far here. So we can somehow continue!
	// We will need these to be static.
	static ArrayList<Route> routes = new ArrayList<Route>();
	static ArrayList<BusLines> busLines = new ArrayList<BusLines>();
	static ArrayList<BusPosition> busPositions = new ArrayList<BusPosition>();
	// We'll probably need another one for the class Bus, if we actually use it.
	// But I guess the Bus itself as a conceptual class should exist.
	
	
	
	
	private static void createTheLists() throws IOException, ParseException {
		
		createRoutes(routes);
		createBusLines(busLines);
		createBusPositions(busPositions);
		
		
		
	}
	
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
            routes.add( new Route(Integer.parseInt(columns[0]), Integer.parseInt(columns[1]), Integer.parseInt(columns[2]), columns[3]));
        }
        in.close();
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
            busLines.add( new BusLines (Integer.parseInt(columns[0]), columns[1], columns[2]));
            
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
        
		
		
	}

	private static void createBusPositions(ArrayList<BusPosition> busPositions) throws IOException, ParseException {
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
            // https://www.mkyong.com/java/how-to-convert-string-to-date-java/  parsing the Date 
            // https://stackoverflow.com/questions/4216745/java-string-to-date-conversion
            DateFormat formatter = new SimpleDateFormat("MMM d yyyy HH:mm:ss:SSSa");
           // String dateInString = "Mar  4 2019 11:50:50:000AM"; // dummy
          // Date date =  formatter.parse(columns[5]); // sql.Date was creating problem
            // vehicleID needs trimming because of whitespace creaint problem
            busPositions.add( new BusPosition (Integer.parseInt(columns[0]), Integer.parseInt(columns[1]),Integer.parseInt(columns[2].trim()), Double.parseDouble(columns[3]), Double.parseDouble(columns[4]), formatter.parse(columns[5])));            
           

           }
          
        
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
        }
		*/
		
		
		
	}


	public static void main(String[] args) throws IOException, ParseException {
		createTheLists();
	// creating this so we see if it works correctly
	}
	
	
	
	
	
}
