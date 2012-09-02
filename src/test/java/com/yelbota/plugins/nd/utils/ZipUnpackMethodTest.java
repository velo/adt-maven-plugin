package com.yelbota.plugins.nd.utils;

import com.yelbota.plugins.nd.stubs.LoggerStub;
import org.codehaus.plexus.util.FileUtils;
import org.testng.annotations.Test;

import java.io.File;

public class ZipUnpackMethodTest extends AbstractUnpackMethodTest {

    @Test
    public void testUnpack() throws Exception {

        File file = FileUtils.resolveFile(new File("."), "src/test/resources/unit/archive.zip");
        File directory = createDirectory("target/unit/archive/zip");

        UnpackMethod unpackMethod = new ZipUnpackMethod(new LoggerStub());
        unpackMethod.unpack(file, directory);
    }
}
