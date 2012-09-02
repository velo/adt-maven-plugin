package com.yelbota.plugins.nd.utils;

import org.codehaus.plexus.logging.Logger;

import java.util.HashMap;

/**
 * @author Aleksey Fomkin
 */
public class DefaultUnpackMethods extends HashMap<String, UnpackMethod> {

    public DefaultUnpackMethods(Logger plexusLogger) {

        super();

        put("zip", new ZipUnpackMethod(plexusLogger));
        put("tgz", new TarGZipUnpackMethod(plexusLogger));
        put("tbz2", new TarBZip2UnpackMethod());
    }
}
