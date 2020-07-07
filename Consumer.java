import java.io.IOException;
import java.awt.Dimension;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.UIManager;



public class Consumer {

		private static int port;
		private static String host;
	    private static Scanner forInput = new Scanner(System.in);

		public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
			UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
			
			while(true) {
				try { 
					boolean invalid = true;
				// Initially we connect to a random broker and then we'll find the right one, as said on the Project.
													//aris 192.168.1.8
		            System.out.println("The System supports 3 Brokers atm. Please select one to connect to. ");
		            System.out.println("To connect to Broker1 please type: 1");
		            System.out.println("To connect to Broker2 please type: 2");
		            System.out.println("To connect to Broker3 please type: 3");
		            String brokerInitialCon = forInput.nextLine();
		            
		            if (brokerInitialCon.equals("1")) {
		            	port = 5555; 
		            	host = "192.168.1.7";
		            }
		            else if (brokerInitialCon.equals("2")) {
		            	port = 6666; 
		            	host = "192.168.1.11";
		            }
		            else if (brokerInitialCon.equals("3")) {
		            	port = 7777;
		            	host = "192.168.1.11";
		            }
		            
		            
					try( Socket socket = new Socket(host, port)){
						PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);	
						BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				    	while(invalid) {
				    		invalid = false;
				    		String answer = fromServer.readLine();
	                
				    		while (!answer.contains("Done")) {
				    			System.out.println(answer);
				    			answer = fromServer.readLine();
				    		}
				    		String brokerRequest = JOptionPane.showInputDialog(null, "Which broker covers your needs. Please insert either Broker1, Broker2, or Broker3");
							System.out.println(brokerRequest);
							toServer.println(brokerRequest);
						
							if((brokerRequest.equals("1") & port==5555) |(brokerRequest.equals("2") & port==6666) | (brokerRequest.equals("3") & port==7777) ) {
								answer = fromServer.readLine();
								String lineRequest = JOptionPane.showInputDialog(null, answer);
								toServer.println(lineRequest);
								String rightBroker = fromServer.readLine();
								

								if(answer.contains("failed")) {
									rightBroker = fromServer.readLine();
									
									JOptionPane.showMessageDialog(null,"We will restart the procedure.");
									invalid = true;
									//break;
								
								}else {
									
									JOptionPane.showMessageDialog(null,rightBroker);
									String busInfo = fromServer.readLine();
									JOptionPane.showMessageDialog(null,busInfo);
									System.out.println(busInfo);
								}
					
							}
		
				    	}
			
		}
				} catch(IOException e) {
					System.err.println("Fatal connection error ");
					e.printStackTrace();
				}	
	}
		}
}


