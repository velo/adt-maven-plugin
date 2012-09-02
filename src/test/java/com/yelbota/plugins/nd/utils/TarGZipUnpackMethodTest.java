package com.yelbota.plugins.nd.utils;

import com.yelbota.plugins.nd.stubs.LoggerStub;
import org.codehaus.plexus.util.FileUtils;
import org.testng.annotations.Test;

import java.io.File;

public class TarGZipUnpackMethodTest extends AbstractUnpackMethodTest {

    @Test
    public void testUnpack() throws Exception {

        File file = FileUtils.resolveFile(new File("."), "src/test/resources/unit/archive.tgz");
        File directory = createDirectory("target/unit/archive/tgz");

        UnpackMethod unpackMethod = new TarGZipUnpackMethod(new LoggerStub());
        unpackMethod.unpack(file, directory);
    }
}
