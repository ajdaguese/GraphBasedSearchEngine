package pa1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import api.Graph;
import api.TaggedVertex;

public class Main {

	public static void main(String[] args) throws IOException {
		Date date = new Date();
		long t1 = date.getTime();
		Crawler c = new Crawler("http://cs.iastate.edu/~smkautz", 1, 100);
		Graph<String> g =  c.crawl();
		date = new Date();
		long t2 = date.getTime();
		ArrayList<TaggedVertex<String>> vertices = g.vertexDataWithIncomingCounts();
		int i;
		int totalEdges = 0;
		for(i = 0; i < vertices.size(); i++)
		{
			System.out.println(vertices.get(i).getVertexData());
			totalEdges += vertices.get(i).getTagValue() - 1;
			System.out.println(g.getNeighbors(i).size());
		}
		
		/*for(i = 0; i < g.getIncoming(0).size(); i++)
		{
			System.out.println("  in: " + g.getIncoming(0).get(i));
		}
		for(i = 0; i < g.getNeighbors(0).size(); i++)
		{
			System.out.println("  out: " + g.getNeighbors(0).get(i));
		}*/
		System.out.println(i);
		System.out.println(totalEdges);
		System.out.println(t2 - t1);
		//100pages10000depth:
		//901
		//172832
		//1000pages10000depth:
		//22838
		//1648542
	}	
}