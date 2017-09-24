import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Matrix {

	public static void main(String[] args) {
		int nb_nodes = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/out.roadNet-CA"));
			String line;
			 while ((line = br.readLine()) != null) {
				 if(!line.contains("%")) {
		    		String[] parts = line.split(" ");
		    		nb_nodes = Math.max(nb_nodes, Math.max(Integer.parseInt(parts[0]), Integer.parseInt(parts[1])));
		    	}
			 }
			 br.close();
			 System.out.println("nb nodes = " + nb_nodes);
			 
			 
			 br = new BufferedReader(new FileReader("data/out.roadNet-CA"));
			 boolean[][] matrix = new boolean[nb_nodes+1][nb_nodes+1];
			 
			 while ((line = br.readLine()) != null) {
				 if(!line.contains("%")) {
		    		String[] parts = line.split(" ");
		    		matrix[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] = true;
		    		matrix[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])] = true;
		    	}
			 }
			 
			 br.close();
			 
         	for(int i=0;i<nb_nodes;i++){
         		for(int j=0;j<nb_nodes;j++){
         			if(matrix[i][j]!=true){
         				matrix[i][j] = false;
         			}
         		}
         	}
         	
         	for(int i=0;i<matrix.length;i++) {
         		for(int j = 0;j<matrix[i].length;j++) {
         			if(matrix[i][j])
         				System.out.print("1");
         			else
         				System.out.print("0");
         		}
         		System.out.println();
         	}
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
