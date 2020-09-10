package pa1;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import api.Graph;
import api.TaggedVertex;

public class ImplementedGraph implements Graph<String>
{
	//a hash map to keep track of which vertex index a url belongs to
	HashMap<String, Integer> index = new HashMap<String, Integer>();
	ArrayList<String> vertices = new ArrayList<String>();
	ArrayList<LinkedList<Integer>> outgoing = new ArrayList<LinkedList<Integer>>();
	ArrayList<LinkedList<Integer>> incoming = new ArrayList<LinkedList<Integer>>();
	ArrayList<TaggedVertex<String>> taggedVertices = new ArrayList<TaggedVertex<String>>();
	@Override
	public ArrayList<String> vertexData() 
	{
		// TODO Auto-generated method stub
		return vertices;
	}

	@Override
	public ArrayList<TaggedVertex<String>> vertexDataWithIncomingCounts() 
	{
		return taggedVertices;
	}

	@Override
	public List<Integer> getNeighbors(int index) 
	{
		return outgoing.get(index);
	}

	@Override
	public List<Integer> getIncoming(int index) 
	{
		return incoming.get(index);
	}
	public void addVertex(String newUrl, String existingUrl)
	{
		index.put(newUrl, vertices.size());
		vertices.add(newUrl);
		incoming.add(new LinkedList<Integer>());
		outgoing.add(new LinkedList<Integer>());
		if(existingUrl != null)
		{
			int existingI = index.get(existingUrl);
			incoming.get(vertices.size()-1).add(existingI);
			outgoing.get(existingI).add(vertices.size()-1);
		}
	}
	public int getIndex(String url)
	{
		return index.get(url);
	}
	public void makeTaggedVertices()
	{
		for(int i = 0; i < vertices.size(); i++)
		{
			//I add one to the indegree to account for the possibility of the seed have no incoming edges
			taggedVertices.add(new TaggedVertex<String>(vertices.get(i), incoming.get(i).size()+1));
		}
	}
	public void addEdge(String to, String from)
	{
		int toIndex = index.get(to);
		int fromIndex = index.get(from);
		incoming.get(toIndex).add(fromIndex);
		outgoing.get(fromIndex).add(toIndex);
	}
}
