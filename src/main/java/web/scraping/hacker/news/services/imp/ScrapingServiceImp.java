package web.scraping.hacker.news.services.imp;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import web.scraping.hacker.news.converters.HtmlNewsArticleConverter;
import web.scraping.hacker.news.domain.NewsArticle;
import web.scraping.hacker.news.services.ScrapingService;

import java.io.IOException;
import java.util.List;

/**
 * The ScrapingServiceImp class implements the ScrapingService interface and provides methods for scraping news articles from a URL.
 */
public class ScrapingServiceImp implements ScrapingService {

    private final WebClient webClient;
    private final HtmlNewsArticleConverter htmlNewsArticleConverter;

    public ScrapingServiceImp(final HtmlNewsArticleConverter htmlNewsArticleConverter) {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        this.htmlNewsArticleConverter = htmlNewsArticleConverter;
    }

    /**
     * Retrieves a list of NewsArticle objects by scraping the news articles from the specified URL.
     *
     * @param url the URL to scrape the news articles from
     * @return a list of NewsArticle objects
     * @throws IOException if an I/O error occurs while fetching the web page
     */
    @Override
    public List<NewsArticle> getNewsFromUrl(final String url) throws IOException {
        final HtmlPage page = this.webClient.getPage(url);
        final DomNodeList<DomNode> rows = page.querySelectorAll("tr.athing, tr.athing + tr");

        return this.htmlNewsArticleConverter.convertAll(rows);
    }
}
