Bugzilla Tasks Provider
===============================

A plugin for viewing bugs from Bugzilla within IntelliJ/Jetbrains familiy of IDEs.

## Usage

### Install the plugin

* Download the most recent release from the [Releases](https://github.com/alebianco/bugzilla-intellij-task-provider/releases) page.
* Open the Settings menu in IntelliJ and go to the `Plugins` section.
* Click on "Install plugin from disk..." and select the zip file you downloaded.
* Restart IntelliJ when prompted.

### Setup the plugin

* Open the Settings menu in Intellij and go to the `Tasks > Servers` section.
* Add a new server and select **Bugzilla** from the list.
* Fill the information about the server url and your account data.
* Click on "Test" to verify if the plugin can connect to your Bugzilla installation.

**NOTE** If you need to connect to your Bugzilla installation over SSL, and you receive the message "_peer not authenticated_" when you test the server, you might need to add the server's certificate to your Java trust store.
Follow [these instructions](https://confluence.atlassian.com/display/JIRA/Connecting+to+SSL+services) to resolve the problem.

## Building

### How to configure the project

* Open the project settings from `File > Project structure` and go to the `SDKs` section.
* Add a new `IntelliJ Platform Plugin SDK` and navigate to the folder where IntelliJ Ultimate is installed (**$IDEA_HOME** from now on), or the *.app on Mac OSX).
* Move to the `Global libraries` section.
* Add a new _Java_ library add select all jars under the `$IDEA_HOME/plugins/tasks` folder.
* Add both the SDK and the library to the project's module.
* From the module's 'Dependencies' tab add a new `Jars or directories...` and select the `libs` folder of the project.

**NOTE** The plugin and all the libraries it depends on have to build with JDK 1.6 in order to work with IntelliJ 13.

### How to build a plugin

* From the `Build` menu, choose `Make Project`.
* From the `Build` menu, choose `Prepare Plugin Model <name> for Deployment`.

## Contributing

If you want to contribute to the project refer to the [CONTRIBUTING.md](CONTRIBUTING.md) document for guidelines.

## Roadmap

You can follow the project planning on [Trello](https://trello.com/b/z3vBqq9F), you can even vote for the tasks that are more important for you and you'll like to see implemented.
