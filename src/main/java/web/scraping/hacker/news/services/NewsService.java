package web.scraping.hacker.news.services;

import web.scraping.hacker.news.domain.NewsArticle;

import java.util.List;

public interface NewsService {
    List<NewsArticle> filterNewsByLongTitlesSortedByComments(List<NewsArticle> news);

    List<NewsArticle> filterNewsByShortTitlesSortedByPoints(List<NewsArticle> news);
}
