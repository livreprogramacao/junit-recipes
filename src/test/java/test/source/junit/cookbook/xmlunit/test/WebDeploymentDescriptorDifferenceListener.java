package junit.cookbook.xmlunit.test;

import org.custommonkey.xmlunit.*;
import org.w3c.dom.Node;

public class WebDeploymentDescriptorDifferenceListener
        implements DifferenceListener {

    public int differenceFound(Difference difference) {
        if (difference.getId()
                == DifferenceConstants.CHILD_NODELIST_SEQUENCE_ID) {

            if (difference
                    .getControlNodeDetail()
                    .getXpathLocation()
                    .indexOf("init-param")
                    >= 0) {

                return RETURN_IGNORE_DIFFERENCE_NODES_SIMILAR;
            } else {
                return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
            }
        }

        return DifferenceListener.RETURN_ACCEPT_DIFFERENCE;
    }

    public void skippedComparison(Node control, Node test) {
    }

}
