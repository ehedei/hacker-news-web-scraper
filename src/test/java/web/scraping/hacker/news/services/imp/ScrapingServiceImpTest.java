package web.scraping.hacker.news.services.imp;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.AbstractDomNodeList;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.scraping.hacker.news.converters.HtmlNewsArticleConverter;
import web.scraping.hacker.news.converters.imp.HtmlNewsArticleConverterImp;
import web.scraping.hacker.news.domain.NewsArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ScrapingServiceImpTest {
    private static final HtmlNewsArticleConverter htmlNewsArticleConverter = new HtmlNewsArticleConverterImp();

    @Test
    @DisplayName("getNewsFromUrl should a throw IOException when request fails")
    void getNewsFromUrlShouldThrowsException() {
        final String url = "foo";
        final ScrapingServiceImp scrapingService = new ScrapingServiceImp(htmlNewsArticleConverter);
        final Exception exception = assertThrows(IOException.class, () -> scrapingService.getNewsFromUrl(url),
                "Should throw IOException");
        final String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(url), "Exception message should contain url");
    }

    @Test
    @DisplayName("getNewsFromUrl should return a list of NewsArticle")
    void getNewsFromUrl() throws IOException {
        final ScrapingServiceImp scrapingService = new ScrapingServiceImp(htmlNewsArticleConverter);
        final WebClient webClient = mock(WebClient.class);
        final HtmlPage htmlPage = mock(HtmlPage.class);
        final DomNodeList<DomNode> domNodeList = new AbstractDomNodeList<>(null) {
            @Override
            protected List<DomNode> provideElements() {
                return new ArrayList<>();
            }
        };

        when(webClient.getPage(anyString())).thenReturn(htmlPage);
        when(htmlPage.querySelectorAll(anyString())).thenReturn(domNodeList);

        scrapingService.setWebClient(webClient);

        final List<NewsArticle> news = scrapingService.getNewsFromUrl("foo");
        assertNotNull(news, "Should return a list of NewsArticle");
    }
}