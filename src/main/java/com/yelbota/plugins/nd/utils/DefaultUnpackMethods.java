/**
 * Copyright (C) 2012 https://github.com/yelbota/native-dependency-maven-plugin-base
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        put("tar.gz", new TarGZipUnpackMethod(plexusLogger));
        put("tbz2", new TarBZip2UnpackMethod());
    }
}
