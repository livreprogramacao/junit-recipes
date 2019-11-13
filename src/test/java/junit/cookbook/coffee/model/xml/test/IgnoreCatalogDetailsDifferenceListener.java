package junit.cookbook.coffee.model.xml.test;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Set;

public class IgnoreCatalogDetailsDifferenceListener
        implements DifferenceListener {

    private static final Set tagNamesToIgnore =
            new HashSet() {
                {
                    add("unit-price");
                    add("total-price");
                    add("subtotal");
                }
            };

    public int differenceFound(Difference difference) {
        int response = RETURN_ACCEPT_DIFFERENCE;

        int differenceId = difference.getId();
        if (DifferenceConstants.TEXT_VALUE_ID
                == differenceId) {

            String currentTagName =
                    getCurrentTagName(difference);

            if (tagNamesToIgnore.contains(currentTagName)) {
                response =
                        RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }
        } else if (
                DifferenceConstants.ATTR_VALUE_ID
                        == differenceId) {

            Attr attribute = getCurrentAttribute(difference);

            if ("id".equals(attribute.getName())
                    && "item".equals(
                    attribute
                            .getOwnerElement()
                            .getNodeName())) {

                response =
                        RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            }
        }

        return response;
    }

    public void skippedComparison(
            Node expectedNode,
            Node actualNode) {
        // Intentionally do nothing
    }

    public Attr getCurrentAttribute(Difference difference) {
        Node currentNode =
                difference.getControlNodeDetail().getNode();

        return (Attr) currentNode;
    }

    public String getCurrentTagName(Difference difference) {
        Node currentNode =
                difference.getControlNodeDetail().getNode();

        return getTagName(currentNode);
    }

    public String getTagName(Node currentNode) {
        if (currentNode instanceof Text)
            return currentNode.getParentNode().getNodeName();
        else
            return currentNode.getNodeName();
    }

}
