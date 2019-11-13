package junit.cookbook.essays;

import java.io.File;

public class RequestPoller {
    private FileLister fileLister;
    private RequestProcessor processor;

    public RequestPoller(FileLister fileLister, RequestProcessor processor) {
        this.fileLister = fileLister;
        this.processor = processor;
    }

    public void poll() {
        File[] filesToProcess = fileLister.listFiles();
        processor.process(filesToProcess);
    }
}
