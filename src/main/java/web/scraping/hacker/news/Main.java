package web.scraping.hacker.news;

import web.scraping.hacker.news.converters.HtmlNewsArticleConverter;
import web.scraping.hacker.news.converters.imp.HtmlNewsArticleConverterImp;
import web.scraping.hacker.news.domain.NewsArticle;
import web.scraping.hacker.news.services.NewsService;
import web.scraping.hacker.news.services.ScrapingService;
import web.scraping.hacker.news.services.imp.NewsServiceImp;
import web.scraping.hacker.news.services.imp.ScrapingServiceImp;

import java.io.IOException;
import java.util.List;

public class Main {

    private static final String DEFAULT_API_URL = "https://news.ycombinator.com/";

    public static void main(String[] args) {
        String apiUrl = DEFAULT_API_URL;

        if (args.length > 0) {
            apiUrl = args[0];
        }

        final HtmlNewsArticleConverter htmlNewsArticleConverter = new HtmlNewsArticleConverterImp();
        final ScrapingService scrapingService = new ScrapingServiceImp(htmlNewsArticleConverter);
        final NewsService newsService = new NewsServiceImp();

        try {
            final List<NewsArticle> newsArticles = scrapingService.getNewsFromUrl(apiUrl);
            System.out.println("Scraped News:\n");
            newsArticles.forEach(System.out::println);

            final List<NewsArticle> longTitleArticles = newsService.filterNewsByLongTitlesSortedByComments(newsArticles);
            System.out.println("\n\nAll previous entries with more than five words in the title ordered by the number of comments first:\n");
            longTitleArticles.forEach(System.out::println);

            final List<NewsArticle> shortTitleArticles = newsService.filterNewsByShortTitlesSortedByPoints(newsArticles);
            System.out.println("\n\nAll previous entries with less than or equal to five words in the title ordered by points:\n");
            shortTitleArticles.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}