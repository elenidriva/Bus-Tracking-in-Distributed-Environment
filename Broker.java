import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import javax.xml.bind.DatatypeConverter;
import java.net.ServerSocket;
import java.net.Socket;

public class Broker implements Runnable {
	static HashMap<String, ArrayList<Bus>> busMap = new HashMap<>();
	//busMap.add
	static ArrayList<Topic> topics = new ArrayList<Topic>();
	private static HashMap<String, ArrayList<Topic>> hashedTopic = new HashMap<>();
	Consumer consumer = new Consumer();
	Socket connection;
	private static int port = 5555;
	static ServerSocket client;

    public Broker(Socket socket) {
		this.connection = socket;
	}



	public static void main(String[] args) throws Exception {
    	createTopic(topics);
    	
        try (Socket socket = new Socket("localhost", port)) {

    	//	while(true) {
    			ConnectionWithConsumer brokCons = new ConnectionWithConsumer(socket);
    		

    			Thread serverThread = new Thread(brokCons);
    			serverThread.start();
    			serverThread.join();
           // }
        } catch (ConnectException e) {
            System.out.println("Waiting for Consumer!");
        }
      //  sha1("021"); //busLineCode
      // sha1("022");
      // sha1("049");   
        
    }

    public static class ConnectionWithConsumer implements Runnable {
        private static Socket connected;

        ConnectionWithConsumer(Socket connected) {
            this.connected = connected;
        }

        public void run() {
            try {
				startConnection();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    

    



        private static void startConnection() throws IOException {
		System.out.println("THE CLIENT" + " " + connected.getInetAddress() + ":" + connected.getLocalPort() + " IS CONNECTED ");
        PrintWriter fromBroker = new PrintWriter(connected.getOutputStream(), true);
        BufferedReader toBroker = new BufferedReader(new InputStreamReader(connected.getInputStream()));

        hashedTopic = Broker.sha1(topics);
		
		
        }	
	}
    
	// This will be used for hasing String lineID and IP+Port
	public static HashMap<String, ArrayList<Topic>> sha1(ArrayList<Topic> topics) throws UnknownHostException {
		 HashMap<String,ArrayList<Topic>> hashedTopics = new HashMap<>();
	    String sha1 = null;
        long temp = 0;
        for (Topic topic: topics){
            try {
                temp = convertIP(InetAddress.getLocalHost().getHostAddress());

            long num = Integer.parseInt(topic.getLineID()) + temp + 4321;
	    	System.out.println(num);
	        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
	        msdDigest.update(topic.getLineID().getBytes("UTF-8"), 0, topic.getLineID().length());
	        sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
	    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
	        Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, e);
	    }
	}
	
	    System.out.println(sha1);
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
	            topics.add( new Topic (columns[1]));
  
	        }
	        in.close();
		
	}

	public static Long convertIP(String addr) {
		String[] addrArray = addr.split("\\.");
		long num = 0;
		for (int i=0;i<addrArray.length;i++) {
			int power = 3-i;
			num += ((Integer.parseInt(addrArray[i])%256 * Math.pow(256,power)));
		}
		return num;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
    	
	// Hashing works correctly.

} 