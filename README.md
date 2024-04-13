# EsTools
A general purpose Essentials like plugin with everything you need but not messing up anything (MC 1.0+)

EsTools is a plugin designed to be like EssentialsX in that it provides heaps of 
helpful commands, but unlike essentials it doesn't mess up or override vanilla features (e.g. the give command). 
This plugin doesn't override existing Minecraft commands and therefore is less likely to mess stuff up.

Commands:
[View Them In Our Wiki](https://github.com/CoPokBl/EsTools/wiki/commands)

## Support
The plugin is designed to work on every public spigot build. Any issues found on legacy versions
should be reported. Due to the size of the task, we can't test every new feature on every version.
If you find a bug, please report it on the [GitHub Issues](https://github.com/CoPokBl/EsTools/issues).

To find out what versions have been tested look at TestedVersions.txt in the project.
We will likely be slow to test new Minecraft versions, but you can probably assume 
that EsTools will be fully functional on new versions.

**We officially support Spigot/Bukkit/Paper 1.0.0 and above.**

For support DM us at: Discord: @copokbl and @calcilore. Or email support@serble.net.

## Building
To build the plugin you need to have maven installed. You can build with:
```shell
mvn package
```
The plugin will be in the target folder.

Our builds are built against Java 8. We can't guarantee that the plugin will work on Java 7 or below.
Language features from Java 8 are used in the plugin.

## Contributing
We are open to contributions. If you want to contribute to the project, please fork the repository and make a pull request.

**DO NOT CONTRIBUTE CODE WITH JAVA 9+ FEATURES**