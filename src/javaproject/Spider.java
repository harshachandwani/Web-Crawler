package javaproject;

import java.util.*;

public class Spider
{
  Set<String> alreadyVisited = new HashSet<String>();
  List<String> toBeVisited = new LinkedList<String>();
  static final int CRAWL_LIMIT = 20;

  public void search_word(String url, String searchWord)
  {
      while(alreadyVisited.size() < CRAWL_LIMIT)
      {
          String currentUrl;
          SpiderCrawl crawler = new SpiderCrawl();
          if(toBeVisited.isEmpty())
          {
              currentUrl = url;
              alreadyVisited.add(url);
          }
          else
          {
              currentUrl = nextPage();
          }
          crawler.crawl_page(currentUrl); 
          boolean success = crawler.searchForWord(searchWord);
          if(success)
          {
              System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
              break;
          }
          toBeVisited.addAll(crawler.getLinks());
      }
      System.out.println("\nCrawl Complete " + alreadyVisited.size() + " web page(s)");
  }

  public String nextPage()
  {
      String nextPage;
      do
      {
          nextPage = toBeVisited.remove(0);
      } while(alreadyVisited.contains(nextPage));
      alreadyVisited.add(nextPage);
      return nextPage;
  }
}