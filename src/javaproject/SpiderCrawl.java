package javaproject;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class SpiderCrawl
{
	static final int SUCCESS = 200;
	List<String> linksOnPage = new LinkedList<String>();
    Document htmlDocument;	
        
    List<String> getLinks()
    {
        return linksOnPage;
    }
    
    /* Makes the http requests*/
    boolean crawl_page(String url)
    {
        try
        {
            Connection connection = Jsoup.connect(url).userAgent("");
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == SUCCESS) 
            {
                System.out.println("\nCrawling " + url);
            }
           
            Elements linksOnPage = htmlDocument.select("a[href]");
            System.out.println("Number of links on the page: " + linksOnPage.size() + " links");
            for(Element link : linksOnPage)
            {
                this.linksOnPage.add(link.absUrl("href"));
            }
            return true;
        }
        catch(IOException ioe)
        {
           //HTTP Request Failure
            return false;
        }
    }
    public boolean searchForWord(String searchWord)
    {
        if(htmlDocument == null)
        {
            return false;
        }
        System.out.println("Word to lookup: " + searchWord + "...");
        return htmlDocument.body().text().toLowerCase().contains(searchWord.toLowerCase());
    }

}

