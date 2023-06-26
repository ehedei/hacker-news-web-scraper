package web.scraping.hacker.news.services;

import web.scraping.hacker.news.domain.NewsArticle;

import java.io.IOException;
import java.util.List;

/**
 * The ScrapingService interface provides a method for scraping news articles from a URL.
 */
public interface ScrapingService {

    /**
     * Retrieves a list of NewsArticle objects by scraping the news articles from the specified URL.
     *
     * @param url the URL to scrape the news articles from
     * @return a list of NewsArticle objects
     * @throws IOException if an I/O error occurs while fetching the web page
     */
    List<NewsArticle> getNewsFromUrl(String url) throws IOException;
}
