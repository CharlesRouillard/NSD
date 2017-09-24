import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;


public class CleanGraph {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader("data/out.roadNet-CA"));
	    HashSet<String> lines = new HashSet<String>();
	    String line;  
	    String[] tab;
	    
	    while ((line = reader.readLine()) != null) {
	    	if(!line.contains("%")) {
	    		tab = line.split(" ");
	    		if(!lines.contains(tab[0] + " " + tab[1]) && !lines.contains(tab[1] + " " + tab[0]) && !tab[0].equals(tab[1])) {
	    			lines.add(line);
	    		}
	    	}
	    }
	    
	    reader.close();
	    
	    BufferedWriter writer = new BufferedWriter(new FileWriter("data/out.roadNet-CA"));
	    for (String unique : lines) {
	    	writer.write(unique);
	    	writer.newLine();
	    }
	    writer.close();
	}
}
