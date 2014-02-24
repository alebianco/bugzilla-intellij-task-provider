bugzilla-intellij-task-provider
===============================

A task provider for Bugzilla4 that actually works.

# Steps to configure a plugin SDK:

* Open Module Settings
* SDKs -> + button -> IntelliJ Platform Plugin SDK -> Choose a folder with IntelliJ Ultimate(!) or *.App on Mac
* Create a new Global library -> add all jars under the $IDEA_HOME/plugins/tasks folder

# How to build a plugin zip file

* Create a new artifact, set it to be build on every Make action.
* Add the content of the META-INF folder and compile output.
* Get the compiled zip from the $PROJECT_ROOT$/out/artifacts/ folder
