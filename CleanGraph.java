import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * 
 * @authors ZEGHLACHE Adel & ROUILLARD Charles
 *
 */
public class CleanGraph {
	
	/**
	 * 
	 * @param args the file or pathname
	 * @throws IOException if the file does not exist
	 */
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1) {
			System.err.println("Need to pass a filename or a path in argument");
		}
		else {
			/*read the file pass in args*/
			String file = args[0];
			BufferedReader reader = new BufferedReader(new FileReader(file));
		    HashSet<String> lines = new HashSet<String>();
		    String line;  
		    String[] tab;
		    
		    /**
		     * We're using a hashSet to automaticaly delete duplicate lines in the file
		     * then we test if the 2 numbers on the same line is identical, then remove it
		     * and then removes duplicate edges like 1-2 and 2-1
		     */
		    while ((line = reader.readLine()) != null) {
		    	if(!line.contains("%")) {
		    		tab = line.split(" ");
		    		if(!lines.contains(tab[0] + " " + tab[1]) && !lines.contains(tab[1] + " " + tab[0]) && !tab[0].equals(tab[1])) {
		    			lines.add(line);
		    		}
		    	}
		    }
		    
		    reader.close();
		    
		    /*Once this done, write the new graph in the same file*/
		    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		    for (String unique : lines) {
		    	writer.write(unique);
		    	writer.newLine();
		    }
		    writer.close();
		}
	}
}
