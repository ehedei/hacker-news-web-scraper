package web.scraping.hacker.news.converters;

import com.gargoylesoftware.htmlunit.html.DomNode;
import web.scraping.hacker.news.domain.NewsArticle;

import java.util.List;

/**
 * The HtmlNewsArticleConverter interface defines methods for converting a list of DomNodes into a list of NewsArticle objects.
 */
public interface HtmlNewsArticleConverter {

    /**
     * Converts a list of DomNodes into a list of NewsArticle objects.
     *
     * @param source the list of DomNodes to convert
     * @return a list of NewsArticle objects
     */
    List<NewsArticle> convertAll(List<DomNode> source);
}
