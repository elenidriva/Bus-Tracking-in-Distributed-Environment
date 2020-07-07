import java.io.*;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import javax.xml.bind.DatatypeConverter;
import java.net.ServerSocket;
import java.net.Socket;

public class Broker implements Runnable {
	Socket connection;
	
	static ArrayList<Topic> topics = new ArrayList<>();
	private static ArrayList<Topic> b1List = new ArrayList<>();
	private static ArrayList<Topic> b2List = new ArrayList<>();
	private static ArrayList<Topic> b3List = new ArrayList<>();
	private static HashMap<Topic,  ArrayList<Value>> serverResults = new HashMap<>();
	private static int port1 = 5555;
	private static int port2 = 6666;
	private static int port3 = 7777;
	static ServerSocket client;
	boolean invalid;
	

    public Broker(Socket socket) {
		this.connection = socket;
	}

	public Broker() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static void main(String[] args) throws Exception {
    	createTopic(topics);
    	ServerSocket socket = new ServerSocket(port1); 		// Modifying this for the rest of the brokers.
    	//ServerSocket socket = new ServerSocket(port2);
    	//ServerSocket socket = new ServerSocket(port3);
        try {

    		while(true) {
    			Socket connection = socket.accept();

    	        HashMap<String, ArrayList<Topic>> hashedTopic = sha1(topics);
    			String broker = initiate(connection);
    			if(broker.equals("1") && connection.getLocalPort()==5555) {
    				Thread consThread = new Thread(new ConnectionWithConsumer(connection, broker, hashedTopic));
    				consThread.start();          	 
    			} else if(broker.equals("2") && connection.getLocalPort()==6666) {
    				Thread consThread = new Thread(new ConnectionWithConsumer(connection, broker, hashedTopic));
    				consThread.start();          	 
    			} else if(broker.equals("3") && connection.getLocalPort()==7777) {
    				Thread consThread = new Thread(new ConnectionWithConsumer(connection, broker, hashedTopic));
    				consThread.start();           	
    				}else {
    				//socket.close();
    				if(broker.equals("1")) {
    	        		ServerSocket socket1 = new ServerSocket(5555, 3);
    	                Socket connection1 = socket1.accept();
    	                Thread consThread1 = new Thread(new ConnectionWithConsumer(connection1, broker,hashedTopic));
    	                consThread1.start();  
    				} else if (broker.equals("2")) {
    	        		ServerSocket socket2 = new ServerSocket(6666, 3);
    	                Socket connection2 = socket2.accept();
    	                Thread consThread2 = new Thread(new ConnectionWithConsumer(connection2, broker,hashedTopic));
    	                consThread2.start();  
    				
    				}else if(broker.equals("3")) {
    	        		ServerSocket socket3 = new ServerSocket(7777, 3);
    	                Socket connection3 = socket3.accept();
    	                Thread consThread3 = new Thread(new ConnectionWithConsumer(connection3, broker,hashedTopic));
    	                consThread3.start();  
    					
    				}
    			}
             
    		}
        } catch (ConnectException e) {
            System.out.println("Waiting for Consumer!");
        }
        
    }

	public static String initiate(Socket connection) throws IOException {
		
		System.out.println("New Client with adrdress" + " " + connection.getInetAddress() + " has connected on port: " + connection.getLocalPort()); // it's the default port they always connect there first
		
		//System.out.println(hashedTopic);
        PrintWriter fromBroker = new PrintWriter(connection.getOutputStream(), true);
        BufferedReader toBroker = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
        HashMap<String, ArrayList<Topic>> hashedTopic = sha1(topics);
        boolean invalid = true;
        while(invalid) {
        	invalid = false;
        	fromBroker.println("We will provide you a full list of Brokers who are responsible for the lines");
        	String brokerList = "Broker1 handles the topics: ";
        	for (Topic topic : hashedTopic.get("1")) brokerList = brokerList + topic.getLineID() + " ";
        	// fromBroker.println("Broker1 handles the topics: " + brokerList);
        	brokerList = brokerList +"\nBroker2 handles the topics: ";
        	// fromBroker.println("Broker2 handles the topics: ");
        	for (Topic topic : hashedTopic.get("2")) brokerList = brokerList + topic.getLineID() + " ";
        	brokerList = brokerList +"\nBroker3 handles the topics: ";
        	// fromBroker.println("Broker3 handles the topics: ");
        	for (Topic topic : hashedTopic.get("3")) brokerList = brokerList + topic.getLineID() + " ";
        	fromBroker.println(brokerList);
        	fromBroker.println("Done");
        }
        	
        	String brokerSelection = toBroker.readLine();
		
		return brokerSelection;	
	}
	
	
    public static class ConnectionWithConsumer implements Runnable {
        private static Socket connected;
        private static String broker;
		private static HashMap<String, ArrayList<Topic>> hashedTopic;
        ConnectionWithConsumer(Socket connected, String broker, HashMap<String, ArrayList<Topic>> hashedTopic) {
            ConnectionWithConsumer.connected = connected;
            ConnectionWithConsumer.broker = broker;
            ConnectionWithConsumer.hashedTopic = hashedTopic;
        }

        public void run() {
            try {
            	//HashMap<String, ArrayList<Topic>> hashedTopic = sha1(topics);
				startConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    
        private static void startConnection() throws IOException, ParseException {
		System.out.println("New Client with adrdress" + " " + connected.getInetAddress() + " has connected on port: " + connected.getLocalPort()); // it's the default port they always connect there first
		//System.out.println(hashedTopic);
        PrintWriter fromBroker = new PrintWriter(connected.getOutputStream(), true);
        BufferedReader toBroker = new BufferedReader(new InputStreamReader(connected.getInputStream()));
        
       	fromBroker.println("Please insert the busLine you want from " + broker);
       		String lineSelection = toBroker.readLine();
       		boolean correctLine = false;
        		
        		for (Topic topic : hashedTopic.get(broker)) if (topic.getLineID().equals(lineSelection)) correctLine = true;
        		ArrayList<Value> busInfo;
        		if(correctLine) {
        			fromBroker.println("Your request is being processed shortly. We're establishing connection with the server, please wait!");
        			boolean serverConnection = true;
        			 
                    while (serverConnection) {
                        try (Socket clientSocket = new Socket("localhost", 5678)) {
                        	serverConnection = false;
                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                            out.writeObject(broker);
                            serverResults = new Broker().pull(clientSocket);
                        } catch (ConnectException | ClassNotFoundException e) {
                        	e.printStackTrace();    
                        }
                    }
                    
                    try {
                      
                        for (Topic topic : serverResults.keySet()) {
                            if (topic.getLineID().equals(lineSelection.trim())) {
                                busInfo = serverResults.get(topic);
                                busInfo.sort(Comparator.comparing(b -> b.getBus().getTimestampOfBusPosition()));
                                // This creates errors in some computers...
                                // String temp= "Mar  4 2015 10:38:56:000AM";
                                // DateFormat formatter = new SimpleDateFormat("MMM d yyyy HH:mm:ss:SSSa");
                                // Date date = formatter.parse(temp);
                                // for (Value mostRecent : busInfo)
                                // 	if(date.compareTo(mostRecent.getBus().getTimestampOfBusPosition())<0)
                                //		date = mostRecent.getBus().getTimestampOfBusPosition();
                                //System.out.println("The bus with id " + display.getBus().getVehicleID() + " was last spotted at [" + display.getBus().getTimestampOfBusPosition() + "] at \nLatitude: " + display.getBus().getLatitude() + "\nLongitude: " + display.getBus().getLongitude() + "\nRoute: " + display.getBus().getLineID() + "\n-----------------------------------------------------------\n");
                                for (Value display : busInfo) {
                                	 //  if(display.getBus().getTimestampOfBusPosition().compareTo(date)==0)
                                		  fromBroker.println("[" + display.getBus().getTimestampOfBusPosition() +" | Route: " + display.getBus().getLineID()+" | BusID: " +display.getBus().getVehicleID() +
                                				  				" ] Last-spotted at [Latitude]: " +display.getBus().getLatitude()+ " [Longitude]: "+ display.getBus().getLongitude() );
                                }
                                
                                if (busInfo.size() == 0)
                                    System.out.println("You've inserted wrong broker.");
                                    fromBroker.println("You've inserted wrong broker.");
                            }
                        }
                    } catch (NullPointerException e) {
                        fromBroker.println("0 values");
                    }
                    fromBroker.println("more");
        		}else {
        			System.out.println("I'm here and customer failed on broker line");
        			fromBroker.println("Request failed. The line you've inserted doesn't belong to Broker's1 responsibility.");
 
        		}
        }
       }
	
    
	// This will be used for hashing String lineID and IP+Port
    // We adjust it so that it doesn't hash a String, but each string of our list.
	public static HashMap<String, ArrayList<Topic>> sha1(ArrayList<Topic> topics) throws UnknownHostException {
		 HashMap<String,ArrayList<Topic>> hashedTopics = new HashMap<>();
	    String sha1 = null;
        int IPadd;
        for (Topic topic: topics){
            try {
            	sha1 = null;
               IPadd = convertIP(InetAddress.getLocalHost().getHostAddress());
               int lineID = Integer.parseInt(topic.getLineID());
               int toHash = lineID + IPadd + port1;

               MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
               msdDigest.update(Integer.toString(toHash).getBytes("UTF-8"), 0, Integer.toString(toHash).length());
               sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
               //System.out.println("sha1: " +sha1); 
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            	Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, e);
            }
	
            //System.out.println(sha1);
            sha1 = sha1.replaceAll("[^\\d.]", "");
            if (sha1.length()>62) sha1 = sha1.substring(60);
            else if( sha1.length()>55 & sha1.length()<=62) sha1 = sha1.substring(53);
            else if (sha1.length()>=47 & sha1.length()<=54) sha1 = sha1.substring(45);
            else if(sha1.length()>=39 & sha1.length()>47) sha1= sha1.substring(37);
            else if(sha1.length()<=30 & sha1.length()> 39) sha1 = sha1.substring(28);
            else if (sha1.length()>=23 & sha1.length()<30) sha1 = sha1.substring(21);
            else if(sha1.length()>16 & sha1.length()<23) sha1 = sha1.substring(14);
            else if(sha1.length()<=16 & sha1.length()>9) sha1 = sha1.substring(6);
            else sha1 = sha1.substring(5);
            //System.out.println(sha1);
            int hashedval = Integer.parseInt(sha1.trim());
           // System.out.println(hashedval);
            
            if(hashedval%3==0) {
        	    b1List.add(topic);
            }else if(hashedval%3==1) { 
            	b2List.add(topic);
            }else {
            	b3List.add(topic);
            }
        }	
        /*
        System.out.println("b1");
        for (int i = 0; i< b1.hashedList.size() ; i++){
        	 System.out.println(b1.hashedList.get(i).getLineID());	
        }
        System.out.println("b2");
        for (int i = 0; i< b2.hashedList.size() ; i++){
       	 System.out.println(b2.hashedList.get(i).getLineID());	
       }
        System.out.println("b3");
        for (int i = 0; i< b3.hashedList.size() ; i++){
       	 System.out.println(b3.hashedList.get(i).getLineID());	
       }
        */
        
        hashedTopics.put("1", b1List);
        hashedTopics.put("2", b2List);
        hashedTopics.put("3", b3List);


	    return hashedTopics;
	}

	
	public static void createTopic(ArrayList<Topic> topics) throws IOException{
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
	            topics.add( new Topic (columns[1].trim()));
  
	        }
	        in.close();
		System.out.println("printing in create topics class" +topics.get(1).getLineID() +"" + topics.get(2).getLineID());
	}

	public static int convertIP(String addr) {
		String[] addrArray = addr.split("\\.");
		int ip = 0;
		for (int i=0; i<addrArray.length; i++) {
			int power = 3 - i;
			ip += ((Integer.parseInt(addrArray[i]) % 256 * Math.pow(256,power)));
		}
		return ip;
	}
	
	
    HashMap<Topic, ArrayList<Value>> pull(Socket clientSocket) throws IOException, ClassNotFoundException{
        HashMap<Topic, ArrayList<Value>> input = null;
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            while (true) {
                try {
                    Object fromServer;
                    fromServer = in.readObject();
                    if(!fromServer.equals("Stop")){
                        input = (HashMap<Topic, ArrayList<Value>>) fromServer;
                    }else{
                        break;
                    }
                } catch (EOFException ignored) {

                }
            }
            clientSocket.close();
        }catch (BindException | ConnectException e){
            System.out.println("Couldn't connect to server");
        }
        return input;
    }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
    	
	// Hashing works correctly.

} 