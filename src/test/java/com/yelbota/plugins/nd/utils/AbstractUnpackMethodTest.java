package com.yelbota.plugins.nd.utils;

import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;

public abstract class AbstractUnpackMethodTest {

    protected final File pwd = new File(".");

    protected File createDirectory(String path) throws IOException {

        File file = FileUtils.resolveFile(pwd, path);

        if (file.exists()) {

            FileUtils.cleanDirectory(file);
            file.delete();
        }

        file.mkdirs();
        return file;
    }
}
