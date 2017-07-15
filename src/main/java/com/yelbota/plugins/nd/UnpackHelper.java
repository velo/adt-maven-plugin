/**
 * Copyright (C) 2017 Marvin Herman Froeder (marvin@marvinformatics.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yelbota.plugins.nd;

import org.sonatype.aether.spi.connector.ArtifactDownload;
import com.yelbota.plugins.nd.utils.UnpackMethod;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Aleksey Fomkin
 */
public class UnpackHelper {

    /**
     * @author Aleksey Fomkin
     */
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

        unpack(directory, artifact, unpackMethods, null);
    }

    public void unpack(File directory, Artifact artifact,
            Map<String, UnpackMethod> unpackMethods,
            Log log) throws UnpackHelperException {
        unpack(directory, artifact.getType(), artifact.getFile(), unpackMethods, log);
    }

    public void unpack(File directory, ArtifactDownload artifactDownload,
            Map<String, UnpackMethod> unpackMethods) throws UnpackHelperException {

        unpack(directory, artifactDownload, unpackMethods, null);
    }

    public void unpack(File directory, ArtifactDownload artifactDownload,
            Map<String, UnpackMethod> unpackMethods,
            Log log) throws UnpackHelperException {
        org.sonatype.aether.artifact.Artifact artifact = artifactDownload.getArtifact();
        if (artifact != null) {
            unpack(directory, artifact.getExtension(), artifactDownload.getFile(), unpackMethods, log);
        } else {
            new MojoFailureException(artifactDownload + " has no valid artifact reference.");
        }
    }

    /**
     * Unpack `artifact` to `directory`.
     * @throws UnpackHelperException
     */
    private void unpack(File directory, String artifactType, File artifactFile,
            Map<String, UnpackMethod> unpackMethods,
            Log log) throws UnpackHelperException {

        if (directory.exists()) {
            if (log != null) log.info("dir '" + directory + "' exists");

            if (directory.isDirectory()) {
                if (log != null) log.info("already unpacked?");
                logAlreadyUnpacked();
            } else {
                new MojoFailureException(directory.getAbsolutePath() + ", which must be directory for unpacking, now is file");
            }
        } else {
            if (log != null) log.info("dir '" + directory + "' does not exist");
            try {
                logUnpacking();
                directory.mkdirs();
                if (log != null) log.info("getting method for artifact type " + artifactType);
                UnpackMethod unpackMethod = unpackMethods.get(artifactType);
                if (log != null) log.info("artifact file: " + artifactFile);
                unpackMethod.unpack(artifactFile, directory, log);
            } catch (IOException e) {
                throw new UnpackHelperException("Can't unpack " + artifactFile, e);
            } catch (UnpackMethod.UnpackMethodException e) {
                throw new UnpackHelperException("Can't unpack " + artifactFile, e);
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