package pa1;

import java.util.ArrayList;
import java.util.List;

public class Vertex 
{
	private ArrayList<Vertex> outgoingEdges = new ArrayList<Vertex>();
	private ArrayList<Vertex> incomingEdges = new ArrayList<Vertex>();
	private String url;
	
	public Vertex(String url)
	{
		this.url = url;
	}
	
	public void addIncomingEdge(Vertex v)
	{
		incomingEdges.add(v);
	}
	public void addOutgoingEdge(Vertex v)
	{
		outgoingEdges.add(v);
	}
	public List<Vertex> getIncoming()
	{
		return incomingEdges;
	}
	public List<Vertex> getOutgoing()
	{
		return outgoingEdges;
	}
	public String getUrl()
	{
		return this.url;
	}
}
