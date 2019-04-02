import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;
import javax.xml.bind.DatatypeConverter;

public class Broker {
	static HashMap<String, ArrayList<Bus>> busMap = new HashMap<>();
	//busMap.add
	
 
/**
 * Hashing with SHA1
 *
 * @param input String to hash
 * @return String hashed
 */
	// This will be used for hasing String lineID and IP+Port
	public static String sha1(String input) {
	    String sha1 = null;
	    try {
	        MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
	        msdDigest.update(input.getBytes("UTF-8"), 0, input.length());
	        sha1 = DatatypeConverter.printHexBinary(msdDigest.digest());
	    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
	        Logger.getLogger(Broker.class.getName()).log(Level.SEVERE, null, e);
	    }
	    System.out.println(sha1);
	    return sha1;
	}
	
	
	
	
	// Hashing works correctly.
    public static void main(String[] args) throws Exception {
        sha1("021"); //busLineCode
        sha1("022");
        sha1("049");
        
        
    }
} 