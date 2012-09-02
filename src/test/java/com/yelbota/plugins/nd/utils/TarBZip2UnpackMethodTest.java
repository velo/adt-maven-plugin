package com.yelbota.plugins.nd.utils;

import org.codehaus.plexus.util.FileUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class TarBZip2UnpackMethodTest extends AbstractUnpackMethodTest {

    @Test
    public void testUnpack() throws Exception {

        File file = FileUtils.resolveFile(pwd, "src/test/resources/unit/archive.tbz2");
        File directory = createDirectory("target/unit/archive/tbz2");

        UnpackMethod unpackMethod = new TarBZip2UnpackMethod();

        String os = System.getProperty("os.name").toLowerCase();

        if (!(os.indexOf("mac") > -1 || os.indexOf("lin") > -1)) {

            try {
                unpackMethod.unpack(file, directory);
                Assert.fail("TBzip2UnpackMethod runned not on mac or linux should throws UnpackException");
            }
            catch (UnpackMethod.UnpackMethodException e) {
                // Ok.
            }
        }
        else {
            unpackMethod.unpack(file, directory);
        }
    }
}
