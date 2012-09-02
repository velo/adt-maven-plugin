package com.yelbota.plugins.nd.utils;

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;

/**
 *  @author Aleksey Fomkin
 */
public interface UnpackMethod {

    public class UnpackMethodException extends Exception {

        public UnpackMethodException(String message) {
            super(message);
        }

        public UnpackMethodException(String message, Throwable exception) {
            super(message, exception);
        }
    }

    void unpack(File file, File directory) throws IOException, UnpackMethodException;

    void unpack(File file, File directory, Log log) throws IOException, UnpackMethodException;
}
