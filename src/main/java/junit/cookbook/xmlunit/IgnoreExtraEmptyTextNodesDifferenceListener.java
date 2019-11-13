package junit.cookbook.xmlunit;

import org.apache.crimson.tree.TextNode;
import org.custommonkey.xmlunit.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class IgnoreExtraEmptyTextNodesDifferenceListener
        implements DifferenceListener {

    public int differenceFound(Difference difference) {
        Node controlNode = difference.getControlNodeDetail().getNode();
        Node testNode = difference.getTestNodeDetail().getNode();

        NodeList controlNodeNonemptyChildren =
                getNonemptyChildNodes(controlNode);
        NodeList testNodeNonemptyChildren =
                getNonemptyChildNodes(testNode);

        switch (difference.getId()) {
            case DifferenceConstants.HAS_CHILD_NODES_ID:
            case DifferenceConstants.CHILD_NODELIST_LENGTH_ID:
                boolean nonemptyChildrenListsSameSize =
                        controlNodeNonemptyChildren.getLength()
                                == testNodeNonemptyChildren.getLength();

                return (nonemptyChildrenListsSameSize)
                        ? RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR
                        : RETURN_ACCEPT_DIFFERENCE;

            default:
                return RETURN_ACCEPT_DIFFERENCE;
        }

    }

    private NodeList getNonemptyChildNodes(Node nodeParameter) {
        boolean deepClone = true;
        Node node = nodeParameter.cloneNode(deepClone);

        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node each = childNodes.item(i);
            if (each.getNodeType() == Node.TEXT_NODE) {
                TextNode textNode = (TextNode) each;
                String textIgnoringWhitespace =
                        String.valueOf(textNode.getText()).trim();

                if (textIgnoringWhitespace.length() == 0)
                    node.removeChild(each);
            }
        }

        return childNodes;
    }

    public void skippedComparison(Node control, Node test) {
    }

}
