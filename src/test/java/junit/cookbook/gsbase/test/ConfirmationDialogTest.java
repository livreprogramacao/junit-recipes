package junit.cookbook.gsbase.test;

import com.gargoylesoftware.base.testing.EventCatcher;
import junit.framework.TestCase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.util.Arrays;
import java.util.List;

public class ConfirmationDialogTest extends TestCase {

    public void testEvents() throws Exception {
        JOptionPane optionPane =
                new JOptionPane(
                        "Are you sure?!",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.YES_NO_CANCEL_OPTION);

        EventCatcher eventCatcher = new EventCatcher();
        eventCatcher.listenTo(optionPane);

        JFrame mainFrame = new JFrame("The Main Frame");
        Container mainContentPane =
                mainFrame.getContentPane();
        mainContentPane.add(optionPane);

        HierarchyEvent expectedHierarchyEvent =
                new HierarchyEvent(
                        optionPane,
                        HierarchyEvent.HIERARCHY_CHANGED,
                        optionPane,
                        mainContentPane,
                        HierarchyEvent.PARENT_CHANGED);

        List expectedEvents =
                Arrays.asList(
                        expectedHierarchyEvent);

        eventCatcher.assertEventsAppearEquals(expectedEvents);
    }
}
