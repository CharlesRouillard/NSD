import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EdgesList {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("data/out.roadNet-CA"));
		ArrayList<String> al = new ArrayList<String>();
		try 
		{
			String line;
			String[] tab;
			while((line = br.readLine()) != null) {
				if(!line.contains("%")) {
					tab = line.split(" ");
					al.add("(" + tab[0] + "," + tab[1] + ")");
				}
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		br.close();
		
		for(int i = 0;i<al.size();i++) {
			System.out.println(al.get(i));
		}
		
		System.out.println("done");
	}
}
