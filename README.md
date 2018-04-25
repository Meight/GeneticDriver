# GeneticDriver

## Preview

![20 AI cars learning.](https://github.com/Meight/GeneticDriver/blob/master/docs/images/preview-learning.png "20 AI cars learning.")

20 AI cars learning how to drive through the use of raycasts.

![Player versus AI.](https://github.com/Meight/GeneticDriver/blob/master/docs/images/preview-vs-ai.png "Player versus the best stored AI.")

Player vers the best stored AI (all-time best individual from the training phases).

## Run the application

Grab the latest build [here](https://github.com/Meight/GeneticDriver/tree/master/builds) or head to the ``builds/`` folder.

To run the application, simply run one of the root scripts:
* ``start.bat`` for Windows-based platforms;
* ``start.sh`` for Linux-based platforms.

You can check these scripts before running them to ensure they're safe.

## In-game controls

* press <kbd>SPACE</kbd> to make your car accelerate;
* press <kbd>←</kbd> to turn left and <kbd>→</kbd> to turn right.

## Build the project

You will need [Maven](https://maven.apache.org/) to build the project using the preconfigured ``pom.xml`` file.

To clean the project from previous builds, including ``target`` temporary folder, first run 
```mvn clean```.

Then compile, package and assemble the project by running
```mvn compile package assembly:single```.

This will generate ``.zip`` and ``.tag.gz`` archives in ``target`` temporary folder.

## Authors

* [Matthieu Le Boucher](https://github.com/Meight)
* [Guilhem Cichocki](https://github.com/gcichocki)
* [Vincent Pera](https://github.com/VincentPera)

## Details

Since the project's goal was more to work around the game's and machine learning logic than its aesthetics, it uses the [Slick2D](http://slick.ninjacave.com/) library for rendering purposes, which requires itself a few other libraries such as [LWJGL](https://www.lwjgl.org/) or [JInput](https://github.com/jinput/jinput) (hence the ``.dll``s at the project's root).

The cars' "physics" as well as the neural network and the netcode, on which we focused more, are totally hand-made.
