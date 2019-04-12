import java.io.IOException;
import java.awt.Dimension;
import java.io.*;
import java.net.*;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.UIManager;



public class Consumer {
		BufferedReader fromServer;
		PrintWriter toServer;
		private static int port=5555;
		ServerSocket socket = null;

		public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
			UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
			
			//while(true) {
				try { 
					boolean invalid = true;
				// Initially we connect to a random broker and then we'll find the right one, as said on the Project.
				
					Socket socket = new Socket("localhost", port); 	// B1 connection

					Consumer consumer = new Consumer();  
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
					
						if(brokerRequest.equals("Broker1")) {
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
				
						} else if (brokerRequest.equals("Broker2")) {
							socket.close();
							Socket socket2 = new Socket("localhost", 6666);
							PrintWriter toServer2 = new PrintWriter(socket2.getOutputStream(), true);	
							BufferedReader fromServer2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
							
							String answer2 = fromServer2.readLine();
							String lineRequest = JOptionPane.showInputDialog(null, answer2);
							toServer2.println(lineRequest);
							String rightBroker = fromServer2.readLine();
							JOptionPane.showMessageDialog(null, rightBroker);
							
							String busInfo = fromServer2.readLine();
							JOptionPane.showMessageDialog(null,busInfo);
							System.out.println(busInfo);
							
							
						}else if (brokerRequest.equals("Broker3")) {
							
							
							
						}
			    	}
	
	
				} catch(IOException e) {
					System.err.println("Fatal connection error ");
					e.printStackTrace();
				}	
	
			}
		//}

}


