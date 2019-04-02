import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Publisher {
	// We will store all the data we've made so far here. So we can somehow continue!
	// We will need these to be static.
	static ArrayList<Route> routes = new ArrayList<Route>();
	static ArrayList<BusLines> busLines = new ArrayList<BusLines>();
	static ArrayList<BusPosition> busPositions = new ArrayList<BusPosition>();
	// We'll probably need another one for the class Bus, if we actually use it.
	// But I guess the Bus itself as a conceptual class should exist.
	
	
	
	
	private static void createTheLists() throws IOException {
		
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
            System.out.println(columns[0] + columns[1] + columns[2] + columns[3]);
            //need to convert from string to int for the columns int routeCode, lineCode, routeType;
            // valueOf-> Integer Object or parseInt -> int
            routes.add( new Route(Integer.parseInt(columns[0]), Integer.parseInt(columns[1]), Integer.parseInt(columns[2]), columns[3]));
        }
        in.close();
        
        /* Testing the Output. Works accordingly!
        System.out.println(routes.get(0).getRouteCode());
        System.out.println(routes.get(0).getLineCode());
        System.out.println(routes.get(0).getRouteType());
        System.out.println(routes.get(0).getRouteDescription());
        */
	}
	private static void createBusLines(ArrayList<BusLines> busLines) {
	
		
	}

	private static void createBusPositions(ArrayList<BusPosition> busPositions) {
		
		
	}


	public static void main(String[] args) throws IOException {
		createTheLists();
	// creating this so we see if it works correctly
	}
	
	
	
	
	
}
