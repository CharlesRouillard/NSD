import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class DegreeGraph 
{
	public static void main(String[] args) throws IOException 
	{
		BufferedReader br = new BufferedReader(new FileReader("data/out.roadNet-CA"));
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		try 
		{
			String line;
			String[] tab;
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					tab = line.split(" ");
					if(hm.get(tab[0]) != null) {
						hm.put(tab[0], hm.get(tab[0])+1);
					}
					else {
						hm.put(tab[0],1);
					}
					
					if(hm.get(tab[1]) != null) {
						hm.put(tab[1], hm.get(tab[1])+1);
					}
					else {
						hm.put(tab[1],1);
					}
				}
			}
			
			//System.out.println(hm.toString());
			for(Map.Entry<String, Integer> entry : hm.entrySet()) {
				System.out.println("The node " + entry.getKey() + " has a degree of " + entry.getValue());
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
	}
}
