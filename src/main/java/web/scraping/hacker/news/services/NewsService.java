package web.scraping.hacker.news.services;

import web.scraping.hacker.news.domain.NewsArticle;

import java.util.List;

/**
 * The NewsService interface provides methods for filtering and sorting news articles.
 */
public interface NewsService {

    /**
     * Filters the news articles by long titles and sorts them by the number of comments in ascending order.
     *
     * @param news the list of news articles to filter and sort
     * @return a list of filtered and sorted news articles
     */
    List<NewsArticle> filterNewsByLongTitlesSortedByComments(List<NewsArticle> news);

    /**
     * Filters the news articles by short titles and sorts them by the points in ascending order.
     *
     * @param news the list of news articles to filter and sort
     * @return a list of filtered and sorted news articles
     */
    List<NewsArticle> filterNewsByShortTitlesSortedByPoints(List<NewsArticle> news);
}
