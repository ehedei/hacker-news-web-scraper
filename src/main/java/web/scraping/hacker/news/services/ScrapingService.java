package web.scraping.hacker.news.services;

import web.scraping.hacker.news.domain.NewsArticle;

import java.io.IOException;
import java.util.List;

public interface ScrapingService {
    List<NewsArticle> getNewsFromUrl(String url) throws IOException;
}
