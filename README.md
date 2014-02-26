bugzilla-intellij-task-provider
===============================

A task provider for Bugzilla4 that actually works.

# How to configure the project

* Open Module Settings
* SDKs -> + button -> IntelliJ Platform Plugin SDK -> Choose a folder with IntelliJ Ultimate(!) or *.App on Mac
* Create a new Global library -> add all jars under the $IDEA_HOME/plugins/tasks folder

# How to build a plugin

* From the `Build` menu, choose `Make Project`
* From the `Build` menu, choose `Prepare Plugin Model <name> for Deployment`
