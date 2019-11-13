package junit.cookbook.coffee.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileSystemMailService implements MailService {
    public void sendMessage(
            String fromAddress,
            String toAddress,
            String subject,
            String bodyText) {

        try {
            File messageFile = createMessageFile();

            PrintWriter out =
                    new PrintWriter(new FileWriter(messageFile), false);
            out.println("From: " + fromAddress);
            out.println("To: " + toAddress);
            out.println("Subject: " + subject);
            out.println();
            out.println(bodyText);
            out.flush();
            out.close();
        } catch (IOException e) {
            throw new ServiceException(
                    "Unable to create message file",
                    e);
        }

    }

    private File createMessageFile() throws IOException {
        return File.createTempFile(
                "test-mail",
                ".txt",
                new File(System.getProperty("java.io.tmpdir")));
    }
}
