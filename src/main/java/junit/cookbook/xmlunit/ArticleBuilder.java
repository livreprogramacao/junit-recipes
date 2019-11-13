package junit.cookbook.xmlunit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArticleBuilder {

    private String title;
    private String authorName;
    private List paragraphs;
    private List headings;

    public ArticleBuilder(String title) {
        this.title = title;
        this.paragraphs = new ArrayList();
        this.headings = new ArrayList();
    }

    public void addAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void addParagraph(String text) {
        this.paragraphs.add(text);
    }

    public void addHeading(String text) {
        this.headings.add(text);
    }

    public String toXml() {
        StringBuffer buffer =
                new StringBuffer("<?xml version=\"1.0\" ?><article>");

        writeXmlTag(buffer, "title", title);
        writeXmlTag(buffer, "author", authorName);

        writeBodyElements(buffer, headings, "h1");
        writeBodyElements(buffer, paragraphs, "p");

        buffer.append("</article>");
        return buffer.toString();
    }

    private void writeBodyElements(
            StringBuffer buffer,
            List bodyElements,
            String bodyElementTag) {

        for (Iterator i = bodyElements.iterator(); i.hasNext(); ) {
            String each = (String) i.next();
            writeXmlTag(buffer, bodyElementTag, each);
        }
    }

    private void writeXmlTag(
            StringBuffer buffer,
            String tagName,
            Object tagValue) {

        buffer.append("<" + tagName + ">").append(tagValue).append(
                "</" + tagName + ">");
    }

}
