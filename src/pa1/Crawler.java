package pa1;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import api.Graph;
import api.Util;

/**
 * Implementation of a basic web crawler that creates a graph of some
 * portion of the world wide web.
 *
 * @author PLEASE FILL IN TEAM MEMBER NAMES HERE
 */
public class Crawler
{
  /**
   * Constructs a Crawler that will start with the given seed url, including
   * only up to maxPages pages at distance up to maxDepth from the seed url.
   * @param seedUrl
   * @param maxDepth
   * @param maxPages
   */
	private String sUrl;
	private int mDepth;
	private int mPages;
	boolean pageFlag = false;
  public Crawler(String seedUrl, int maxDepth, int maxPages)
  {
    sUrl = seedUrl;
    mDepth = maxDepth;
    mPages = maxPages;
  }
  
  /**
   * Creates a web graph for the portion of the web obtained by a BFS of the 
   * web starting with the seed url for this object, subject to the restrictions
   * implied by maxDepth and maxPages.  
   * @return
   *   an instance of Graph representing this portion of the web
   */
  public Graph<String> crawl() throws IOException
  {
	Queue<QueueElement> q = new LinkedList<QueueElement>();
	HashMap<String, Integer> discovered = new HashMap<String, Integer>();
	ImplementedGraph g = new ImplementedGraph();
	g.addVertex(sUrl, null);
	Document currentDoc;
	String currentUrl;
	String currentLink;
	HashMap<String, Integer> edges;
	discovered.put(sUrl, 0);
	q.add(new QueueElement(0, sUrl));
	while(!q.isEmpty())
	{
		//pops the top off the queue off into curr
		QueueElement curr = q.poll();
		currentUrl = curr.url;
		int currentDepth = curr.depth;
		try
		{
			currentDoc = Jsoup.connect(currentUrl).get();
		}
		catch(org.jsoup.HttpStatusException|org.jsoup.UnsupportedMimeTypeException e)
		{
			continue;
		}
		//edges is just used to see if an edge already exists
		edges = new HashMap<String, Integer>();
		Elements links = currentDoc.select("a[href]");
		for (Element link : links)
		{	
			currentLink = link.absUrl("abs:href");
			//if this edge exists in the hashmap, skip
			if(edges.get(currentLink) != null)
			{
				continue;
			}
			if(Util.ignoreLink(currentUrl, currentLink))
			{
				continue;
			}
			if(discovered.get(currentLink) != null)
			{
				//if the link is different from the page we are on, stops a vertex from having an edge to itself
				if(currentLink.compareTo(currentUrl) != 0)
				{
					g.addEdge(currentLink, currentUrl);
				}
			}
			else if(!pageFlag && (mDepth > currentDepth))
			{
				g.addVertex(currentLink, currentUrl);
				q.add(new QueueElement(currentDepth+1, currentLink));
				discovered.put(currentLink, 0);
				//if we have reached the maximum number of pages sets the page flag to true
				if(discovered.size() >= mPages)
				{
					pageFlag = true;
				}
			}
			//arbitrarily uses zero, it only matters if it exists
			edges.put(currentLink, 0);
		}
	}
	g.makeTaggedVertices();
    return g;
  }
}
