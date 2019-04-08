import java.io.IOException;
import java.awt.Dimension;
//TODO The amount of energy should actually be in a file-packet. Will be done later, the logic is the same.
//     Gia tin wra to kanw apla arithmitika..
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
				
			try { 
				UIManager.put("OptionPane.minimumSize",new Dimension(400,200)); 
				Socket socket = new Socket("localhost", port); 	

				Consumer consumer = new Consumer();  
			    PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);	
			    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String response = fromServer.readLine();
                String busRequest =  JOptionPane.showInputDialog(null, response);
                toServer.println(busRequest);	
                String answer = fromServer.readLine();
                
	
	
	
	
			} catch(IOException e) {
				System.err.println("Fatal connection error ");
				e.printStackTrace();
			}	
	
		}

}


