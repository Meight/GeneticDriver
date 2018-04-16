# GeneticDriver

## Run application

Grab the latest build in [https://github.com/Meight/GeneticDriver/tree/master/builds](here) or head to the ``builds/`` folder.

To run the application, simply run one of the root scripts:
* ``start.bat`` for Windows-based platforms;
* ``start.sh`` for Linux-based platforms.

You can check these scripts before running them to ensure they're safe.

## Build project

You will need [Maven](https://maven.apache.org/) to build the project using the preconfigured ``pom.xml`` file.

To clean the project from previous builds, including ``target`` temporary folder, first run 
```mvn clean```

Then compile, package and assemble the project by running
```mvn compile package assembly:single```

This will generate ``.zip`` and ``.tag.gz`` archives in ``target`` temporary folder.

## Authors

* [Matthieu Le Boucher](https://github.com/Meight)
* [Guilhem Cichocki](https://github.com/gcichocki)
* [Vincent Pera](https://github.com/VincentPera)
