package web.scraping.hacker.news.converters.imp;

import com.gargoylesoftware.htmlunit.html.DomNode;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.w3c.dom.Node;
import web.scraping.hacker.news.converters.HtmlNewsArticleConverter;
import web.scraping.hacker.news.domain.NewsArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

public class HtmlNewsArticleConverterImp implements HtmlNewsArticleConverter {

    private static final Pattern NOT_NUMBER_PATTERN = Pattern.compile("\\D");

    @Override
    public List<NewsArticle> convertAll(final List<DomNode> source) {
        final int initialCapacity = source.size() / 2;
        final List<NewsArticle> articles = new ArrayList<>(initialCapacity);

        for (final DomNode row : source) {
            final Node node = row.getAttributes().getNamedItem("id");

            if (node == null) {
                continue;
            }

            final DomNode nextRow = row.getNextSibling();
            articles.add(extractNewsArticleFromAdjacentRows(row, nextRow));
        }

        return articles;
    }

    protected NewsArticle extractNewsArticleFromAdjacentRows(final DomNode row, final DomNode nextRow) {
        final NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(getRowTitle(row));
        getNumberFromNode(row, "span.rank").ifPresent(newsArticle::setIndex);
        getNumberFromNode(nextRow, ".subline .score").ifPresent(newsArticle::setPoints);
        getNumberFromNode(nextRow, ".subline > a:last-child").ifPresent(newsArticle::setNumberOfComments);

        return newsArticle;
    }

    protected String getRowTitle(final DomNode row) {
        final DomNode titleNode = row.querySelector(".titleline > a");
        return Objects.nonNull(titleNode) ? titleNode.asNormalizedText() : StringUtils.EMPTY;
    }

    protected Optional<Integer> getNumberFromNode(final DomNode node, final String cssSelector) {
        Integer number = null;
        final DomNode selectedNode = node.querySelector(cssSelector);

        if (Objects.nonNull(selectedNode)) {
            number = NumberUtils.toInt(RegExUtils.removeAll(selectedNode.asNormalizedText(), NOT_NUMBER_PATTERN));
        }

        return Optional.ofNullable(number);
    }
}
