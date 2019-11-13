package junit.cookbook.util;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageCollector implements BulletinBoard {
    private List messages;
    private StringBuffer currentMessageBuffer = new StringBuffer();

    public MessageCollector() {
        this.messages = new ArrayList();
    }

    public MessageCollector(List messagesBuffer) {
        this.messages = messagesBuffer;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public void printOn(PrintWriter printWriter) {
        for (Iterator i = messages.iterator();
             i.hasNext();
        ) {
            String eachMessage = (String) i.next();
            printWriter.println(eachMessage);
        }
    }

    public void append(String restOfMessage) {
        if (messages.isEmpty()) {
            addMessage(restOfMessage);
        } else {
            int lastMessageIndex = messages.size() - 1;

            String lastMessage =
                    (String) messages.get(lastMessageIndex);

            messages.set(
                    lastMessageIndex,
                    lastMessage + restOfMessage);
        }
    }

    public void print(String partialMessage) {
        currentMessageBuffer.append(partialMessage);
    }

    public void println(String entireMessage) {
        currentMessageBuffer.append(entireMessage);
        addMessage(currentMessageBuffer.toString());
        currentMessageBuffer = new StringBuffer();
    }

    public String getBufferContents() {
        return currentMessageBuffer.toString();
    }
}
