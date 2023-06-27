package web.scraping.hacker.news.converters.imp;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import web.scraping.hacker.news.converters.HtmlNewsArticleConverter;
import web.scraping.hacker.news.domain.NewsArticle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HtmlNewsArticleConverterImpTest {
    private final Random random = new Random();

    private final String rowPattern = "<tr class=\"athing\" id=\"%d\">\n" +
            "<td class=\"title\"><span class=\"rank\">%d.</span></td>" +
            "<td class=\"title\"><span class=\"titleline\"><a>%s</a></td></tr>";
    private final String adjacentRowPattern = "<tr>\n<td class=\"subtext\">\n" +
            "<span class=\"subline\"><span class=\"score\" id=\"score_36491704\">%d points</span>\n" +
            " by <a class=\"hnuser\">User</a>\n <span><a href=\"#\">3 hours ago</a></span>\n" +
            "<span id=\"unv_36491704\"></span> | <a href=\"#\">hide</a> | <a href=\"#\">%d&nbsp;comments</a></span>\n" +
            "</td></tr>";

    private final WebClient webClient = new WebClient();
    private final HtmlNewsArticleConverterImp htmlNewsArticleConverter = new HtmlNewsArticleConverterImp();

    @Test
    @DisplayName("convertAll should return a list of NewsArticle with the size of the scraped rows amount")
    void convertAll() throws IOException {
        final int numberOfArticles = getRandomNumber(0, 10);
        final StringBuilder html = new StringBuilder("<html><head></head><body><table>");

        for (int i = 1; i <= numberOfArticles; i++) {
            final String row = String.format(rowPattern, i, i, "Hacker news number " + i);
            html.append(row);

            final String adjacentRow = String.format(adjacentRowPattern, getRandomNumber(1, 999), getRandomNumber(1, 100));
            html.append(adjacentRow);
        }

        html.append("</table></body></html>");

        final HtmlPage htmlPage = webClient.loadHtmlCodeIntoCurrentWindow(html.toString());
        final DomNodeList<DomNode> rows = htmlPage.querySelectorAll("tr");

        final List<NewsArticle> news = htmlNewsArticleConverter.convertAll(rows);

        assertEquals(numberOfArticles, news.size());
    }

    @Test
    @DisplayName("extractNewsArticleFromAdjacentRows should return a NewsArticle with the data scraped")
    void extractNewsArticleFromAdjacentRows() throws IOException {
        final String title = "foo bar ".repeat(getRandomNumber(1, 4)).trim();
        final int index = getRandomNumber(1, 30);
        final int points = getRandomNumber(1, 3500);
        final int comments = getRandomNumber(1, 5000);

        final String row = String.format(rowPattern, index, index, title);
        final String nextRow = String.format(adjacentRowPattern, points, comments);

        final HtmlPage rowNode = webClient.loadHtmlCodeIntoCurrentWindow(row);
        final HtmlPage nextRowNode = webClient.loadHtmlCodeIntoCurrentWindow(nextRow);

        final NewsArticle article = htmlNewsArticleConverter.extractNewsArticleFromAdjacentRows(rowNode, nextRowNode);
        assertNotNull(article, "Extracted NewsArticle should not be null");
        assertEquals(title, article.getTitle(), "Extracted title should be equal to source");
        assertEquals(index, article.getIndex(), "Extracted index should be equal to source");
        assertEquals(comments, article.getNumberOfComments(), "Extracted number of comments should be equal to source");
        assertEquals(points, article.getPoints(), "Extracted points should be equal to source");
    }

    @Test
    @DisplayName("getRowTitle should return the title scraped")
    void getRowTitle() throws IOException {
        final String title = "foo-bar".repeat(getRandomNumber(1, 5)).trim();
        final HtmlPage htmlPage = webClient.loadHtmlCodeIntoCurrentWindow(String.format("<h1 class=\"titleline\"><a>%s</a></h1>", title));
        assertEquals(title, htmlNewsArticleConverter.getRowTitle(htmlPage), "Title scrapped should be equal to source");
    }

    @Test
    @DisplayName("getRowTitle should return an empty String when the title is not scraped")
    void getRowTitleEmpty() throws IOException {
        final HtmlPage htmlPage = webClient.loadHtmlCodeIntoCurrentWindow("<h1 class=\"titleline\"></h1>");
        final String scrapedTitle = htmlNewsArticleConverter.getRowTitle(htmlPage);
        assertTrue(StringUtils.isEmpty(scrapedTitle), "Title scrapped should be an empty String");
    }

    @Test
    @DisplayName("getNumberFromNode should return a number scraped")
    void getNumberFromNode() throws IOException {
        final int number = getRandomNumber(1, 8000);
        final HtmlPage htmlPage = webClient.loadHtmlCodeIntoCurrentWindow(String.format("<h1>Number %d</h1>", number));
        final int scrapedNumber = htmlNewsArticleConverter.getNumberFromNode(htmlPage, "h1").orElse(-1);
        assertEquals(number, scrapedNumber, "Should return the number");
    }

    @Test
    @DisplayName("getNumberFromNode should null when a number is not scraped")
    void getNumberFromEmptyNode() throws IOException {
        final HtmlPage htmlPage = webClient.loadHtmlCodeIntoCurrentWindow("<h1></h1>");
        final Optional<Integer> scrapedNumber = htmlNewsArticleConverter.getNumberFromNode(htmlPage, "h1");
        assertTrue(scrapedNumber.isEmpty(), "Should not return a number");
    }

    private int getRandomNumber(final int min, final int max) {
        return random.nextInt(min, max + 1);
    }
}