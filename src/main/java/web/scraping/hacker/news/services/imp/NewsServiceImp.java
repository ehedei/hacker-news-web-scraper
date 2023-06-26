package web.scraping.hacker.news.services.imp;

import org.apache.commons.lang3.StringUtils;
import web.scraping.hacker.news.domain.NewsArticle;
import web.scraping.hacker.news.services.NewsService;

import java.util.Comparator;
import java.util.List;

public class NewsServiceImp implements NewsService {

    private static final int SHORT_TITLES_LIMIT = 5;

    @Override
    public List<NewsArticle> filterNewsByLongTitlesSortedByComments(final List<NewsArticle> news) {
        return news.stream()
                .filter(article -> StringUtils.split(article.getTitle()).length > SHORT_TITLES_LIMIT)
                .sorted(Comparator.comparingInt(NewsArticle::getNumberOfComments))
                .toList();
    }

    @Override
    public List<NewsArticle> filterNewsByShortTitlesSortedByPoints(final List<NewsArticle> news) {
        return news.stream()
                .filter(article -> StringUtils.split(article.getTitle()).length <= SHORT_TITLES_LIMIT)
                .sorted(Comparator.comparingInt(NewsArticle::getPoints))
                .toList();
    }
}
