package com.diasparsoftware.io.test;

import com.diasparsoftware.java.io.ReaderUtil;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class GetContentAsStringTest extends TestCase {
    public void testEmptyReader() throws Exception {
        Reader empty = new InputStreamReader(
                new ByteArrayInputStream(new byte[0]));

        assertEquals("", ReaderUtil.getContentAsString(empty));
    }

    public void testFiveCharacters() throws Exception {
        Reader empty = new InputStreamReader(
                new ByteArrayInputStream(new byte[]{'a', 'b', 'c', 'd', 'e'}));

        assertEquals("abcde", ReaderUtil.getContentAsString(empty));
    }

}