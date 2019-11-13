package junit.cookbook.util;

import java.io.PrintStream;


public class PrintStreamBulletinBoard
        implements BulletinBoard {

    private PrintStream printStream;

    public PrintStreamBulletinBoard(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void print(String partialMessage) {
        printStream.print(partialMessage);
    }

    public void println(String entireMessage) {
        printStream.println(entireMessage);
    }

}
