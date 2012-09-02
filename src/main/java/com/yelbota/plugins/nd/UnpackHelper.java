package com.yelbota.plugins.nd;

import com.yelbota.plugins.nd.utils.UnpackMethod;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public abstract class UnpackHelper {

    class UnpackHelperException extends MojoFailureException {

        public UnpackHelperException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    //-------------------------------------------------------------------------
    //
    //  Public methods
    //
    //-------------------------------------------------------------------------

    public void unpack(File directory, Artifact artifact,
                       Map<String, UnpackMethod> unpackMethods) throws UnpackHelperException {

        if (directory.exists()) {

            if (directory.isDirectory()) {
                logAlreadyUnpacked();
            } else {
                new MojoFailureException(directory.getAbsolutePath() + ", which must be directory for unpacking, now is file");
            }
        } else {
            try {
                logUnpacking();
                UnpackMethod unpackMethod = unpackMethods.get(artifact.getType());
                unpackMethod.unpack(artifact.getFile(), directory);
            } catch (IOException e) {
                throw new UnpackHelperException("Can't unpack " + artifact, e);
            } catch (UnpackMethod.UnpackMethodException e) {
                throw new UnpackHelperException("Can't unpack " + artifact, e);
            }
        }
    }

    //-------------------------------------------------------------------------
    //
    //  Abstract methods
    //
    //-------------------------------------------------------------------------

    protected void logAlreadyUnpacked() {
        // Empty default implementation.
    }

    protected void logUnpacking() {
        // Empty default implementation.
    }
}