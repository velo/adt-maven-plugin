# `adt-maven-plugin`

[![Build Status](https://travis-ci.org/velo/adt-maven-plugin.svg?branch=master)](https://travis-ci.org/velo/adt-maven-plugin?branch=master)
[![Coverage Status](https://coveralls.io/repos/github/velo/adt-maven-plugin/badge.svg?branch=master)](https://coveralls.io/github/velo/adt-maven-plugin?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/adt-maven-plugin/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.marvinformatics/adt-maven-plugin/)
[![Issues](https://img.shields.io/github/issues/velo/adt-maven-plugin.svg)](https://github.com/velo/adt-maven-plugin/issues)
[![Forks](https://img.shields.io/github/forks/velo/adt-maven-plugin.svg)](https://github.com/velo/adt-maven-plugin/network)
[![Stars](https://img.shields.io/github/stars/velo/adt-maven-plugin.svg)](https://github.com/velo/adt-maven-plugin/stargazers)

This is a fork of [fomkin/adt-maven-plugin](https://github.com/fomkin/adt-maven-plugin). This is based on `1.0.8`

The major change is that this plugin is released to maven central, so no need to play around with `<repositories>` or `<pluginRepositories>`

Air SDK can be downloaded from [Adobe AIR archived page](https://helpx.adobe.com/air/kb/archived-air-sdk-version.html).

Build Adobe AIR applications with your Maven!
=============================================

General purpose
---------------

Some time ago, Adobe released AIR for mobile devices. There was a question: how to package AIR-application automatically? Flexmojos allows you to build only \*.air packages, so I have created the plugin which could work with platform dependent AIR SDK and additionally build packages for mobile devices. ***This plugin is not replacement of Flexmojos***. You still need Flexmojos to build SWF.

Current status
----------------------

* Building AIR, APK, IPA packages
* Native desktop packages (DMG on Mac OSX, EXE on Windows)
* Adobe Native Extensions (ANE) support
* Run custom adt command
* Simple configuration
* No need installing SDK. Plugin downloads it as dependency 
 
Plans
--------------------------------------------

* Keep cherry-picking anything new

Quick start
-----------------------------------------------

First, make sure that your project has `swf` packaging.

    <packaging>swf</packaging>

Add `adt-maven-plugin` into plugins section

    <plugin>
    
        <groupId>com.marvinformatics</groupId>
        <artifactId>adt-maven-plugin</artifactId>
        <version>1.0</version>
        
        <executions>
          <execution>
            <goals>
                <goal>package</goal>
            </goals>
          </execution>
        </executions>
        
        <configuration>
        
            <sdkVersion>3.5</sdkVersion>
            
            <target>ipa-debug</target>
            <keystore>certificate.p12</keystore>
            <storepass>******</storepass>
            <tsa>none</tsa>

            <!-- Required for ipa* targets -->
            <provisioningProfile>myapp.mobileprovision</provisioningProfile>
            
            <!-- 
                 Optional. Application descriptor. By default is 
                 src/main/resources/application-descriptor.xml
            -->
            <descriptor>src/main/flex/Project-app.xml</descriptor>
            
            <!-- 
                 Optional. Replaces versionNumber in application descriptor. Useful
                 for CI. 0.0.0 by default. 
            -->
            <versionNumber>${build.number}</versionNumber>
            
            <!-- 
                 Optional. Replaces versionLabel in application descriptor. 
                 ${project.version} by default.
             -->
            <versionLabel>${project.version}</versionLabel>
            
            <!-- 
                 By default includes lookedup in target/classes directory. Usualy
                 maven-resources-plugin copy here content of src/main/resources.
                 You can change this behaviour by setting <includesRoot> property. 
            -->
            <includes>
                <include>icons</include>
            </includes>
            
            <!-- 
                 Optional. Plugin home directory. For example "${user.home}/.adt" allows to keep SDK always unpacked for many projects.
                 ${project.build.directory} by default.
             -->
            <pluginHome></pluginHome>

            <!-- (iOS only, AIR 3.4 and higher) Enables the telemetry-based ActionScript sampler in iOS 
                 applications. Using this flag lets you profile the application with Adobe Scout. Although
                 Scout can profile any Flash platform content, enabling detailed telemetry gives you 
                 deep insight into ActionScript function timing, DisplayList, Stage3D rendering and more.
                 Note that using this flag will have a slight performance impact, so do not use it for
                 production applications.
            -->
            <sampler>false</sampler>
        </configuration>
    </plugin>

You can configure signing with `build.adt.keystore`, `build.adt.storepass` and `build.adt.mobileprovision` properties.

    mvn package -Dbuild.adt.keystore=certificate.p12 -Dbuild.adt.storepass=******

If you want to use your own SDK package, place it into plugin dependencies. Be aware, that AIR SDK is platform dependent.

    <plugin>
        <groupId>com.marvinformatics</groupId>
        <artifactId>adt-maven-plugin</artifactId>
        <version>1.0</version>
        <dependencies>
            <dependency>
                <groupId>com.adobe.air</groupId>
                <artifactId>air-sdk</artifactId>
                <version>3.5</version>
                <type>zip</type>
                <classifier>${os.family}</classifier>
            </dependency>
        </dependencies>
        ...
    </plugin>

AIR Native Extensions support
-----------------------------------------------
    
ANE support designed in true maven style. Just deploy your extension to maven repository and add dependency. You don't need to include `<extensions>` section in application descriptor. It will be done automatically.

    <dependency>
        <groupId>com.adobe.extensions</groupId>
        <artifactId>vibration</artifactId>
        <version>1.0</version>
        <type>ane</type>
    </dependency>

Note that Flexmojos 4.x doesn't support ANE dependencies, so you need to use Flexmojos 6.x.

Run custom command
-----------------------------------------------

You can run custom ADT command using `command` goal. 

    <plugin>
        <groupId>com.marvinformatics</groupId>
        <artifactId>adt-maven-plugin</artifactId>
        <version>1.0.7</version>
        <configuration>
            <sdkVersion>3.5</sdkVersion>
        </configuration>
        <executions>
            <execution>
                <goals>
                    <goal>command</goal>
                </goals>
                <configuration>
                    <arguments>-certificate -cn cert 1024-RSA ${project.build.directory}/cert.p12 111</arguments>
                </configuration>
            </execution>
        </executions>
    </plugin>

Examples
-----------------------------------------------

[https://github.com/velo/adt-maven-plugin/tree/master/src/it](https://github.com/velo/adt-maven-plugin/tree/master/src/it)

Foreign resources
-----------------------------------------------

* [Apache Maven](http://maven.apache.org)
* [Flexmojos](http://flexmojos.net)
* [Building Adobe AIR Applications](http://help.adobe.com/en_US/air/build/air_buildingapps.pdf)
