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
package com.yelbota.plugins.adt;

import java.io.File;
import java.util.ArrayList;

/* Use by Maven to map XML tags from the POM to Mojo arguments
	See http://maven.apache.org/guides/mini/guide-configuring-plugins.html#Mapping_Complex_Objects
*/
public class Platform {
    public String name;
    public File directory;
    public File options;
    public Boolean extractLibrarySwf;

    public Platform() {
    }

    public Platform(String name, File directory) {
        this.name = name;
        this.directory = directory;
    }

    public Boolean requiresSWFExtraction() {
        return extractLibrarySwf != null && extractLibrarySwf;
    }
}