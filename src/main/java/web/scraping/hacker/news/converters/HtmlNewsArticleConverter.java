package web.scraping.hacker.news.converters;

import com.gargoylesoftware.htmlunit.html.DomNode;
import web.scraping.hacker.news.domain.NewsArticle;

import java.util.List;

public interface HtmlNewsArticleConverter {
    List<NewsArticle> convertAll(List<DomNode> source);
}
