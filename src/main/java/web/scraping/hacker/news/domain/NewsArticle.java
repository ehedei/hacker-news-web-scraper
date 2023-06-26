package web.scraping.hacker.news.domain;

public class NewsArticle {

    private int index;
    private int numberOfComments;
    private int points;
    private String title;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsArticle { " +
                "index = " + index +
                ", title = '" + title + '\'' +
                ", numberOfComments = " + numberOfComments +
                ", points = " + points +
                " }";
    }
}
