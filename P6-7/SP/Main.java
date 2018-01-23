<<<<<<< HEAD:P6-7/SP/Main.java
=======
//package p6;

>>>>>>> ae5207b5fa28cbf6ae36dc93c88568decf5bf317:P6-7/Main.java
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
	public static void main(String[] args) throws IOException {
		Node[] tabNode = new Node[400];
		Node n;
<<<<<<< HEAD:P6-7/SP/Main.java
		double p = 0.3,q = 0.01;
		BufferedWriter writer = new BufferedWriter(new FileWriter("graph.txt"));
=======
		double p = 0.4,q = 0.01;
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/graph.txt"));
>>>>>>> ae5207b5fa28cbf6ae36dc93c88568decf5bf317:P6-7/Main.java
		
		for(int i = 0;i<400;i++) {
			n = new Node();
			n.setNb(i);
			if(i < 100)
				n.setCluster(1);
			else if(i >= 100 && i < 200)
				n.setCluster(2);
			else if(i >= 200 && i < 300) 
				n.setCluster(3);
			else if(i >= 300 && i < 400)
				n.setCluster(4);
			
			tabNode[i] = n;
		}
		
		for(int i = 0;i<400;i++) {
			for(int j = i;j<400;j++) {
				if(i != j) {
					double rand = ThreadLocalRandom.current().nextDouble();
					if(tabNode[i].getCluster() == tabNode[j].getCluster()) {
						if(rand <= p) {
							writer.write(i + "\t" + j + "\n");
						}
					}
					else {
						if(rand <= q) {
							writer.write(i + "\t" + j + "\n");
						}
					}
				}
			}
		}
		System.out.println("file graph.txt created.");
		writer.close();
	}
}
