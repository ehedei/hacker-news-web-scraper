package web.scraping.hacker.news.services.imp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.scraping.hacker.news.domain.NewsArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class NewsServiceImpTest {

    private static final int MAX_NUMBER_ARTICLES = 10;
    private static final int MIN_NUMBER_ARTICLES = 2;
    private static final Random random = new Random();

    private List<NewsArticle> allNews;
    private int numberOfShortNews;
    private int numberOfLongNews;

    @BeforeEach
    void setUp() {
        numberOfLongNews = getRandomNumber();
        numberOfShortNews = getRandomNumber();

        final List<NewsArticle> shortNews = new ArrayList<>(numberOfShortNews);
        for (int i = 1; i <= numberOfShortNews; i++) {
            final NewsArticle newsArticle = new NewsArticle();
            newsArticle.setIndex(i);
            newsArticle.setPoints(getRandomNumber(0, 100));
            newsArticle.setNumberOfComments(getRandomNumber(0, 100));
            newsArticle.setTitle("foo bar");
            shortNews.add(newsArticle);
        }

        final List<NewsArticle> longNews = new ArrayList<>(numberOfLongNews);
        for (int i = 1; i <= numberOfLongNews; i++) {
            final NewsArticle newsArticle = new NewsArticle();
            newsArticle.setIndex(MAX_NUMBER_ARTICLES + i);
            newsArticle.setPoints(getRandomNumber(0, 100));
            newsArticle.setNumberOfComments(getRandomNumber(0, 100));
            newsArticle.setTitle("foo bar".repeat(5));
            longNews.add(newsArticle);
        }

        allNews = new ArrayList<>(numberOfLongNews + numberOfShortNews);
        allNews.addAll(shortNews);
        allNews.addAll(longNews);
    }

    @Test
    @DisplayName("filterNewsByLongTitlesSortedByComments should return empty list if source is empty or null")
    void filterNewsByLongTitlesSortedByCommentsIsEmpty() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        assertTrue(newsServiceImp.filterNewsByLongTitlesSortedByComments(null).isEmpty(),
                "Should return empty list with null parameter");
        assertTrue(newsServiceImp.filterNewsByLongTitlesSortedByComments(new ArrayList<>()).isEmpty(),
                "Should returns empty list with empty list parameter");
    }

    @Test
    @DisplayName("filterNewsByLongTitlesSortedByComments should return list with the right size")
    void filterNewsByLongTitlesSortedByCommentsSize() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        assertEquals(newsServiceImp.filterNewsByLongTitlesSortedByComments(allNews).size(), numberOfLongNews,
                "List should have the correct size");
    }

    @Test
    @DisplayName("filterNewsByLongTitlesSortedByComments should return list sorted correctly")
    void filterNewsByLongTitlesSortedByComments() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        final List<NewsArticle> news = newsServiceImp.filterNewsByLongTitlesSortedByComments(allNews);
        final NewsArticle firstArticle = news.get(0);
        final NewsArticle lastArticle = news.get(news.size()-1);

        assertTrue(firstArticle.getNumberOfComments() <= lastArticle.getNumberOfComments(),
                "First article should have less comments than last article");

    }

    @Test
    @DisplayName("filterNewsByShortTitlesSortedByPoints should return empty list if source is empty or null")
    void filterNewsByShortTitlesSortedByPointsIsEmpty() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        assertTrue(newsServiceImp.filterNewsByShortTitlesSortedByPoints(null).isEmpty(),
                "Should return empty list with null parameter");
        assertTrue(newsServiceImp.filterNewsByShortTitlesSortedByPoints(new ArrayList<>()).isEmpty(),
                "Should return empty list with empty list parameter");
    }

    @Test
    @DisplayName("filterNewsByShortTitlesSortedByPoints should return list with the right size")
    void filterNewsByShortTitlesSortedByPointsSize() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        assertEquals(newsServiceImp.filterNewsByShortTitlesSortedByPoints(allNews).size(), numberOfShortNews,
                "List should have the correct size");
    }

    @Test
    @DisplayName("filterNewsByShortTitlesSortedByPoints should return list sorted correctly")
    void filterNewsByShortTitlesSortedByPoints() {
        final NewsServiceImp newsServiceImp = new NewsServiceImp();
        final List<NewsArticle> news = newsServiceImp.filterNewsByShortTitlesSortedByPoints(allNews);
        final NewsArticle firstArticle = news.get(0);
        final NewsArticle lastArticle = news.get(news.size()-1);

        assertTrue(firstArticle.getPoints() <= lastArticle.getPoints(),
                "First article should have less points than last article");
    }


    private int getRandomNumber() {
        return getRandomNumber(MIN_NUMBER_ARTICLES, MAX_NUMBER_ARTICLES);
    }

    private int getRandomNumber(final int min, final int max) {
        return random.nextInt(min, max + 1);
    }
}