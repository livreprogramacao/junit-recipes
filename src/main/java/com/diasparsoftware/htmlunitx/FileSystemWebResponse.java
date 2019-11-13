package com.diasparsoftware.htmlunitx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

public class FileSystemWebResponse extends InputStreamWebResponse {
    public FileSystemWebResponse(URL url, File contentFile)
            throws FileNotFoundException {

        super(url, new FileInputStream(contentFile));
    }
}
