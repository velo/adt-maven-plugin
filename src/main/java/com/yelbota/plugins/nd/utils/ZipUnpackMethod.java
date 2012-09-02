package com.yelbota.plugins.nd.utils;

import org.apache.maven.plugin.logging.Log;
import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.zip.ZipUnArchiver;
import org.codehaus.plexus.logging.Logger;

import java.io.File;
import java.io.IOException;

/**
 * @author Aleksey Fomkin
 */
public class ZipUnpackMethod implements UnpackMethod {

    //-------------------------------------------------------------------------
    //
    //  Variables
    //
    //-------------------------------------------------------------------------

    private Logger plexusLogger;

    //-------------------------------------------------------------------------
    //
    //  Constructor
    //
    //-------------------------------------------------------------------------

    public ZipUnpackMethod(Logger plexusLogger) {

        this.plexusLogger = plexusLogger;
    }

    //-------------------------------------------------------------------------
    //
    //  UnpackMethod
    //
    //-------------------------------------------------------------------------

    @Override
    public void unpack(File file, File directory) throws IOException, UnpackMethodException {
        unpack(file, directory, null);
    }

    @Override
    public void unpack(File file, File directory, Log log) throws IOException, UnpackMethodException {

        AbstractUnArchiver worker = createWorker();

        worker.enableLogging(plexusLogger);
        worker.setSourceFile(file);
        worker.setDestDirectory(directory);
        worker.extract();
    }

    protected AbstractUnArchiver createWorker() {
        return new ZipUnArchiver();
    }
}
