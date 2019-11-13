package junit.cookbook.essays.test;

import junit.cookbook.essays.FileLister;

import java.io.File;


public class DirectoryFileLister implements FileLister {
    private File directory;

    public DirectoryFileLister(File directory) {
        this.directory = directory;
    }

    public File[] listFiles() {
        return directory.listFiles();
    }

}
