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

public class ScrapingServiceImp implements ScrapingService {

    private final WebClient webClient;
    private final HtmlNewsArticleConverter htmlNewsArticleConverter;

    public ScrapingServiceImp(final HtmlNewsArticleConverter htmlNewsArticleConverter) {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        this.htmlNewsArticleConverter = htmlNewsArticleConverter;
    }

    @Override
    public List<NewsArticle> getNewsFromUrl(final String url) throws IOException {
        final HtmlPage page = this.webClient.getPage(url);
        final DomNodeList<DomNode> rows = page.querySelectorAll("tr.athing, tr.athing + tr");

        return this.htmlNewsArticleConverter.convertAll(rows);
    }
}
