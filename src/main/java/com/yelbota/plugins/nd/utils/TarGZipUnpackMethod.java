package com.yelbota.plugins.nd.utils;

import org.codehaus.plexus.archiver.AbstractUnArchiver;
import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.Logger;

/**
 * @author Aleksey Fomkin
 */
public class TarGZipUnpackMethod extends ZipUnpackMethod {

    public TarGZipUnpackMethod(Logger plexusLogger) {
        super(plexusLogger);
    }

    @Override
    protected AbstractUnArchiver createWorker() {
        return new TarGZipUnArchiver();
    }
}
