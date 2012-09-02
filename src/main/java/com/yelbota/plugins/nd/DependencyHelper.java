package com.yelbota.plugins.nd;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.ArtifactResolutionRequest;
import org.apache.maven.artifact.resolver.ArtifactResolutionResult;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.repository.RepositorySystem;

import java.util.List;

public abstract class DependencyHelper {

    //-------------------------------------------------------------------------
    //
    //  Constants
    //
    //-------------------------------------------------------------------------

    public static final String OS_CLASSIFIER_MAC = "mac";
    public static final String OS_CLASSIFIER_WINDOWS = "windows";
    public static final String OS_CLASSIFIER_LINUX = "linux";

    //-------------------------------------------------------------------------
    //
    //  Inner classes
    //
    //-------------------------------------------------------------------------

    public class ArtifactResolutionException extends MojoFailureException {

        public ArtifactResolutionResult resolutionResult;

        public ArtifactResolutionException(String message, ArtifactResolutionResult resolutionResult) {
            super(message);
            this.resolutionResult = resolutionResult;
        }
    }

    //-------------------------------------------------------------------------
    //
    //  Protected methods
    //
    //-------------------------------------------------------------------------

    protected String getDefaultClassifier() throws MojoFailureException {

        String systemName = System.getProperty("os.name");
        String preparedName = systemName.toLowerCase();

        if (preparedName.indexOf("win") > -1) {
            return OS_CLASSIFIER_WINDOWS;
        } else if (preparedName.indexOf("lin") > -1) {
            return OS_CLASSIFIER_LINUX;
        } else if (preparedName.indexOf("mac") > -1) {
            return OS_CLASSIFIER_MAC;
        } else {
            throw new MojoFailureException(systemName + " is not supported");
        }
    }

    //-------------------------------------------------------------------------
    //
    //  Public methods
    //
    //-------------------------------------------------------------------------

    /**
     * Resolves native dependency artifact. First it checks plugin dependencies
     * compares they with getDefaultArtifactId() and getDefaultGroupId(), then method
     * tries to resolve from repositories using also getDefaultVersion(), getDefaultPackaging()
     * and getDefaultClassifier().
     *
     * @param pluginArtifacts    list of plugin dependencies (inject this with @parameter)
     * @param repositorySystem   inject this with @parameter
     * @param localRepository    inject this with @parameter
     * @param remoteRepositories inject this with @parameter
     * @return Native dependency artifact
     * @throws MojoFailureException
     */
    public Artifact resolve(List<Artifact> pluginArtifacts, RepositorySystem repositorySystem,
                            ArtifactRepository localRepository,
                            List<ArtifactRepository> remoteRepositories) throws MojoFailureException {

        Artifact artifact = null;

        if (pluginArtifacts != null) {

            // Lookup plugin artifacts.
            for (Artifact pluginArtifact : pluginArtifacts) {

                boolean eqGroupId = pluginArtifact.getGroupId().equals(getDefaultGroupId());
                boolean eqArtifactId = pluginArtifact.getArtifactId().equals(getDefaultArtifactId());

                if (eqGroupId && eqArtifactId) {

                    artifact = pluginArtifact;
                    break;
                }
            }
        }

        if (artifact != null) {
            return artifact;
        } else {
            // Okay. Lets download sdk
            if (repositorySystem != null) {
                artifact = repositorySystem.createArtifactWithClassifier(

                        getDefaultGroupId(),
                        getDefaultArtifactId(),
                        getDefaultVersion(),
                        getDefaultPackaging(),
                        getDefaultClassifier()
                );

                ArtifactResolutionRequest request = new ArtifactResolutionRequest();

                request.setArtifact(artifact);
                request.setLocalRepository(localRepository);
                request.setRemoteRepositories(remoteRepositories);

                ArtifactResolutionResult resolutionResult = repositorySystem.resolve(request);

                if (!resolutionResult.isSuccess()) {

                    String message = "Failed to resolve artifact " + artifact;
                    throw new ArtifactResolutionException(message, resolutionResult);
                }
            }

            return artifact;
        }
    }

    //-------------------------------------------------------------------------
    //
    //  Abstract methods
    //
    //-------------------------------------------------------------------------

    abstract protected String getDefaultArtifactId();

    abstract protected String getDefaultGroupId();

    abstract protected String getDefaultVersion();

    abstract protected String getDefaultPackaging();
}
