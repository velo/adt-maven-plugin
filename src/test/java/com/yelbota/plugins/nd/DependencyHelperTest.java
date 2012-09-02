package com.yelbota.plugins.nd;

import com.yelbota.plugins.nd.stubs.ArtifactRepositoryStub;
import com.yelbota.plugins.nd.stubs.RepositorySystemStub;
import junit.framework.Assert;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.testing.stubs.ArtifactStub;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class DependencyHelperTest {

    @Test
    public void testResolveWhenArtifactPresentInPluginDependencies() throws Exception {

        DependencyHelper dependencyHelper = new DependencyHelper() {

            @Override
            protected String getDefaultArtifactId() {
                return "test-artifact";
            }

            @Override
            protected String getDefaultGroupId() {
                return "test-group";
            }

            @Override
            protected String getDefaultVersion() {
                return "1.0";
            }

            @Override
            protected String getDefaultPackaging() {
                return "zip";
            }
        };

        List<Artifact> pluginArtifacts = new ArrayList<Artifact>();
        Artifact artifactStub = new ArtifactStub();
        artifactStub.setArtifactId(dependencyHelper.getDefaultArtifactId());
        artifactStub.setGroupId(dependencyHelper.getDefaultGroupId());

        pluginArtifacts.add(artifactStub);

        Artifact resolvedArtifact = dependencyHelper.resolve(
                pluginArtifacts,
                new RepositorySystemStub(),
                new ArtifactRepositoryStub(),
                new ArrayList<ArtifactRepository>()
        );

        Assert.assertEquals(artifactStub, resolvedArtifact);
    }
}
